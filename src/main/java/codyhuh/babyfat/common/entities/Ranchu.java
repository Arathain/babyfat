package codyhuh.babyfat.common.entities;

import codyhuh.babyfat.BabyFat;
import codyhuh.babyfat.common.entities.goal.OldRanchuBreedGoal;
import codyhuh.babyfat.common.entities.goal.RanchuBreedGoal;
import codyhuh.babyfat.registry.BFBlocks;
import codyhuh.babyfat.registry.BFEntities;
import codyhuh.babyfat.registry.BFItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

public class Ranchu extends AbstractRanchu implements Bucketable {
	private static final float MAX_SIZE = 2f;
	private static final float MIN_SIZE = 0.8f;
	public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Ranchu.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Float> SIZE_A = SynchedEntityData.defineId(Ranchu.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Float> SIZE_B = SynchedEntityData.defineId(Ranchu.class, EntityDataSerializers.FLOAT);
	public static final EntityDataAccessor<Byte> TAIL = SynchedEntityData.defineId(Ranchu.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Ranchu.class, EntityDataSerializers.BOOLEAN);
	public static final Ingredient FOOD_ITEMS = Ingredient.of(BFItems.WATER_LETTUCE.get());
	private float size = -1f;

	public Ranchu(EntityType<? extends AbstractRanchu> type, Level worldIn) {
		super(type, worldIn);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}
	public float getSize() {
		if(size == -1f) {
			float s = Math.min(getSizeA(), getSizeB());
			size = s;
			return s;
		} else {
			return size;
		}
	}
	protected void reloadSize() {
		size = -1;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(1, new RanchuBreedGoal(this, 1.5D));
		//this.goalSelector.addGoal(1, new RanchuBreedGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.25D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.5, 1));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 2.5D);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(BFBlocks.WATER_LETTUCE.get().asItem());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {

		if (reason == MobSpawnType.BUCKET && dataTag != null && dataTag.contains("Variant", 3)) {

			this.setSizeA(dataTag.getFloat("sizeA"));
			this.setSizeB(dataTag.getFloat("sizeB"));
			this.setVariant(dataTag.getInt("Variant"));
			this.setTail(dataTag.getByte("Tail"));

			if (dataTag.contains("Age")) {
				this.setAge(dataTag.getInt("Age"));
			}

		}else {

			if (getVariant() != -1) {
				return spawnDataIn;
			}
			int wCIndex = RanchuSexResolver.RanchuColour.WILD.ordinal();
			int i;
			int base = 4;
			int pat1 = random.nextInt(64);
			int pat2 = random.nextInt(64);
			int baseColour = wCIndex;
			int c1 = wCIndex;
			int c2 = wCIndex;

			i = base + (pat1 << 3) + (pat2 << 3+6) + (baseColour << 3+6+6) + (c1 << 3+6+6+5) + (c2 << 3+6+6+5+5);
			this.setTail(random.nextInt(3));
			this.setVariant(i);
			float gA = Math.min(Math.abs((float)random.nextGaussian())*0.5f, 1f);
			float gB = Math.min(Math.abs((float)random.nextGaussian())*0.5f, 1f);
			this.setSizeA(MIN_SIZE+gA*gA*(MAX_SIZE-MIN_SIZE));
			this.setSizeB(MIN_SIZE+gB*gB*(MAX_SIZE-MIN_SIZE));
			reloadSize();

		}

		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public static boolean checkFishSpawnRules(EntityType<? extends Ranchu> type, LevelAccessor worldIn, MobSpawnType reason, BlockPos p_223363_3_, RandomSource randomIn) {
		return worldIn.getBlockState(p_223363_3_).is(Blocks.WATER) && worldIn.getBlockState(p_223363_3_.above()).is(Blocks.WATER);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TAIL, (byte)0);
		this.entityData.define(VARIANT, -1);
		this.entityData.define(SIZE_A, 1f);
		this.entityData.define(SIZE_B, 1f);
		this.entityData.define(FROM_BUCKET, false);
	}

	private boolean isFromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	@Override
	public boolean fromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	public void setFromBucket(boolean p_203706_1_) {
		this.entityData.set(FROM_BUCKET, p_203706_1_);
	}

	@Override
	public void loadFromBucketTag(CompoundTag compound) {
		Bucketable.loadDefaultDataFromBucketTag(this, compound);
		setSizeA(compound.getFloat("sizeA"));
		setSizeB(compound.getFloat("sizeB"));
		this.setVariant(compound.getInt("Variant"));
		setTail(compound.getByte("Tail"));
		this.setAge(compound.getInt("Age"));
	}

	@Override
	public void saveToBucketTag(ItemStack bucket) {
		CompoundTag compoundnbt = bucket.getOrCreateTag();
		Bucketable.saveDefaultDataToBucketTag(this, bucket);
		compoundnbt.putFloat("sizeA", getSizeA());
		compoundnbt.putFloat("sizeB", getSizeB());
		compoundnbt.putInt("Variant", this.getVariant());
		compoundnbt.putByte("Tail", (byte)getTail());
		compoundnbt.putInt("Age", this.getAge());
	}

	@Override
	public ItemStack getBucketItemStack() {
		return new ItemStack(BFItems.RANCHU_BUCKET.get());
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_EMPTY_FISH;
	}

	public int getVariant() {
		return this.entityData.get(VARIANT);
	}

	public void setVariant(int variant) {
		this.entityData.set(VARIANT, variant);
	}

	public int getTail() {
		return this.entityData.get(TAIL);
	}

	public void setTail(int variant) {
		this.entityData.set(TAIL, (byte)variant);
	}

	public void setSizeA(float s) {
		this.entityData.set(SIZE_A, s);
	}
	public void setSizeB(float s) {
		this.entityData.set(SIZE_B, s);
	}

	public float getSizeA() {
		return this.entityData.get(SIZE_A);
	}
	public float getSizeB() {
		return this.entityData.get(SIZE_B);
	}

	@Override
	public MobType getMobType() {
		return MobType.WATER;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader worldIn) {
		return worldIn.isUnobstructed(this);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("Variant", getVariant());
		compound.putByte("Tail", (byte)getTail());

		compound.putFloat("sizeA", getSizeA());
		compound.putFloat("sizeB", getSizeB());

		compound.putBoolean("FromBucket", this.isFromBucket());
		compound.putBoolean("Bucketed", this.fromBucket());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setVariant(compound.getInt("Variant"));
		setTail(compound.getByte("Tail"));

		setSizeA(compound.getFloat("sizeA"));
		setSizeB(compound.getFloat("sizeB"));

		this.setFromBucket(compound.getBoolean("FromBucket"));
		this.setFromBucket(compound.getBoolean("Bucketed"));
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.isFromBucket();
	}

	protected void updateAir(int p_209207_1_) {
		if (this.isAlive() && !this.isInWaterOrBubble()) {
			this.setAirSupply(p_209207_1_ - 1);
			if (this.getAirSupply() == -20) {
				this.setAirSupply(0);
				this.hurt(damageSources().drown(), 2.0F);
			}
		} else {
			this.setAirSupply(300);
		}

	}

	@Override
	public void baseTick() {
		int i = this.getAirSupply();
		super.baseTick();
		this.updateAir(i);
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}


	@Override
	protected PathNavigation createNavigation(Level worldIn) {
		return new WaterBoundPathNavigation(this, worldIn);
	}

	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
		return !this.fromBucket() && !this.hasCustomName();
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void aiStep() {
		if (!this.isInWater() && this.onGround() && this.verticalCollision) {
			this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
			this.setOnGround(false);
			this.hasImpulse = true;
			this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
		}

		long time = level().getLevelData().getDayTime();

//		if (canFindLettuce() && time % 24000 > 23000 && !this.isBaby()) {
//			setInLoveTime(40);
//		}
//		if (canFindLettuce() && time % 13000 > 12500) {
//			setInLoveTime(40);
//		}
		super.aiStep();
	}

	private boolean canFindLettuce() {
		BlockPos blockpos = blockPosition();
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for(int i = 0; i < 10; ++i) {
			for(int j = 0; j < 10; ++j) {
				for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
					for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
						blockpos$mutable.setWithOffset(blockpos, k, i, l);
						if (level().getBlockState(blockpos$mutable).is(BFBlocks.WATER_LETTUCE.get())) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public void travel(Vec3 travelVector) {
		if (this.isEffectiveAi() && this.isInWater()) {
			this.moveRelative(0.01F, travelVector);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
			}
		} else {
			super.travel(travelVector);
		}
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}

	@Nullable
	@Override
	public Ranchu getBreedOffspring(ServerLevel w, AbstractRanchu ranchuB) {
			Ranchu child = BFEntities.RANCHU.get().create(w);
			RandomSource rand = this.getRandom();
		if (ranchuB instanceof Ranchu r) {
			// Feral + Feral
			int base = pickBase(this, r, random);
			int pat1 = pickPattern(this, r, random, false);
			int pat2 = pickPattern(this, r, random, true);
			int[] mutated = new int[]{-1};
			int baseColour = composeColour(this.getBaseColour(), r.getBaseColour(), rand, c -> mutated[0] = c.ordinal());
			int c1 = composeColour(this.getFirstPatternColour(), r.getFirstPatternColour(), rand, c -> mutated[0] = c.ordinal());
			int c2 = composeColour(this.getSecondPatternColour(), r.getSecondPatternColour(), rand, c -> mutated[0] = c.ordinal());
			if(mutated[0] != -1) {
				baseColour = c1 = c2 = mutated[0];
				base = 0;
			}
			BabyFat.LOGGER.info("Child conceived: \n" + "base: " + base + "\npattern 1: " + pat1 + "\npattern 2: " + pat2 + "\nbasecolour: " + baseColour + getColourName(baseColour)
			+ "\ncolour 1: " + c1 + getColourName(c1) + "\ncolour 2: " + c2 + getColourName(c2) + "\nmutated: " + (mutated[0] != -1));
			child.setTail(random.nextBoolean() ? this.getTail() : ((Ranchu) ranchuB).getTail());
			child.setVariant(base + (pat1 << 3) + (pat2 << 3+6) + (baseColour << 3+6+6) + (c1 << 3+6+6+5) + (c2 << 3+6+6+5+5));
			if(w.getBiome(this.blockPosition()).is(Tags.Biomes.IS_MUSHROOM) && random.nextFloat() > 0.1f) {
				child.setSizeA(Math.max(this.getSizeA(), ((Ranchu) ranchuB).getSizeA()));
				child.setSizeB(Math.max(this.getSizeB(), ((Ranchu) ranchuB).getSizeB()));
				child.reloadSize();
			} else {
				child.setSizeA(random.nextBoolean() ? this.getSizeA() : ((Ranchu) ranchuB).getSizeA());
				child.setSizeB(random.nextBoolean() ? this.getSizeB() : ((Ranchu) ranchuB).getSizeB());
				child.reloadSize();
			}
		}
		child.setPersistenceRequired();

		return child;
	}

	public static String getColourName(int i) {
		return " (" + RanchuSexResolver.RanchuColour.values()[i].name().toLowerCase() + ")";
	}

	public static int pickBase(Ranchu a, Ranchu b, RandomSource r) {
		int out = 0;
		int ego1 = a.getVariant();
		int ego2 = b.getVariant();
		if((2*2*2-1 & ego1) == (2*2*2-1 & ego2) && (2*2*2-1 & ego2) == 4) {
			//out = r.nextInt(4) == 0 ? 4 : 0;
		}
		return out;
	}

	public static int composeColour(RanchuSexResolver.RanchuColour a, RanchuSexResolver.RanchuColour b, RandomSource r, Consumer<RanchuSexResolver.RanchuColour> m) {
		return RanchuSexResolver.getInstance().lookupSuperposition(a,b).collapse(a,b,r,m).ordinal();
	}

	public static int pickPattern(Ranchu a, Ranchu b, RandomSource r, boolean two) {
		int ego1 = a.getVariant();
		int ego2 = b.getVariant();

		if(r.nextFloat() > 0.9) {
			return r.nextInt(32);
		}

		int offs = two ? 9 : 3;
		return r.nextBoolean() ? (2*2*2*2*2*2-1 & ego1 >> offs) : (2*2*2*2*2*2-1 & ego2 >> offs);
	}


	public RanchuSexResolver.RanchuColour getBaseColour() {
		int ego = this.getVariant();
		return RanchuSexResolver.RanchuColour.values()[(2 * 2 * 2 * 2 * 2 - 1 & ego >> 3 + 6 + 6)];
	}

	public static RanchuSexResolver.RanchuColour getBaseColour(int ego) {
		return RanchuSexResolver.RanchuColour.values()[(2 * 2 * 2 * 2 * 2 - 1 & ego >> 3 + 6 + 6)];
	}

	public RanchuSexResolver.RanchuColour getFirstPatternColour() {
		int ego = this.getVariant();
		return RanchuSexResolver.RanchuColour.values()[(2 * 2 * 2 * 2 * 2 - 1 & ego >> 3 + 6 + 6 + 5)];
	}

	public RanchuSexResolver.RanchuColour getFirstPatternColour(int ego) {
		return RanchuSexResolver.RanchuColour.values()[(2 * 2 * 2 * 2 * 2 - 1 & ego >> 3 + 6 + 6 + 5)];
	}

	public RanchuSexResolver.RanchuColour getSecondPatternColour() {
		int ego = this.getVariant();
		return RanchuSexResolver.RanchuColour.values()[(2 * 2 * 2 * 2 * 2 - 1 & ego >> 3 + 6 + 6 + 5 + 5)];
	}

	public static RanchuSexResolver.RanchuColour getSecondPatternColour(int ego) {
		return RanchuSexResolver.RanchuColour.values()[(2 * 2 * 2 * 2 * 2 - 1 & ego >> 3 + 6 + 6 + 5 + 5)];
	}


	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.COD_HURT;
	}

	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(BFItems.RANCHU_SPAWN_EGG.get());
	}

	public InteractionResult mobInteract(Player p_27477_, InteractionHand p_27478_) {
		Optional<InteractionResult> result = Bucketable.bucketMobPickup(p_27477_, p_27478_, this);

		if(result.isPresent() && result.get().consumesAction()){
			return result.get();
		}else {
			ItemStack itemstack = p_27477_.getItemInHand(p_27478_);
			if (this.isFood(itemstack)) {
				int i = this.getAge();
				if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
					this.usePlayerItem(p_27477_, p_27478_, itemstack);
					this.setInLove(p_27477_);
					this.setInLoveTime(24000);
					return InteractionResult.SUCCESS;
				}

				if (this.isBaby()) {
					this.usePlayerItem(p_27477_, p_27478_, itemstack);
					this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
					return InteractionResult.sidedSuccess(this.level().isClientSide);
				}

				if (this.level().isClientSide) {
					return InteractionResult.CONSUME;
				}
			}
		}
		return super.mobInteract(p_27477_, p_27478_);
	}

}
