package com.github.kipperorigin.originlib.particles;

import com.comphenix.protocol.wrappers.WrappedParticle;
import org.bukkit.util.Vector;

public class ParticleEffect {
    private String particleEffectName;
    private WrappedParticle<?> wrappedParticle;
    private Vector initialPosition;
    private Vector offset;
    private String vectorFormula;
    private int particleCount;
    private boolean longDistance;
    private long startDelay;
    private long delayBetweenEmits;
    private int duplicateValue;
    private ParticleEffectBehavior behavior;
    private int repetitionsBeforeBehavior;
    private int totalSteps;
    private int rotationDivisor;

    // Constructor
    public ParticleEffect(String particleEffectName, WrappedParticle<?> wrappedParticle,
                          Vector initialPosition, Vector offset, String vectorFormula,
                          int particleCount, boolean longDistance,
                          long startDelay, long delayBetweenEmits, int duplicateValue,
                          ParticleEffectBehavior behavior, int repetitionsBeforeBehavior,
                          int totalSteps, int rotationDivisor) {
        this.particleEffectName = particleEffectName;
        this.wrappedParticle = wrappedParticle;
        this.initialPosition = initialPosition;
        this.offset = offset;
        this.vectorFormula = vectorFormula;
        this.particleCount = particleCount;
        this.longDistance = longDistance;
        this.startDelay = startDelay;
        this.delayBetweenEmits = delayBetweenEmits;
        this.duplicateValue = duplicateValue;
        this.behavior = behavior;
        this.repetitionsBeforeBehavior = repetitionsBeforeBehavior;
        this.totalSteps = totalSteps;
        this.rotationDivisor = rotationDivisor;
    }

    // Getters and setters
    public String getVectorFormula() {
        return vectorFormula;
    }

    public void setVectorFormula(String vectorFormula) {
        this.vectorFormula = vectorFormula;
    }

    public Vector getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Vector initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Vector getOffset() {
        return offset;
    }

    public void setOffset(Vector offset) {
        this.offset = offset;
    }

    public String getParticleEffectName() {
        return particleEffectName;
    }

    public void setParticleEffectName(String particleEffectName) {
        this.particleEffectName = particleEffectName;
    }

    public WrappedParticle<?> getWrappedParticle() {
        return wrappedParticle;
    }

    public void setWrappedParticle(WrappedParticle<?> wrappedParticle) {
        this.wrappedParticle = wrappedParticle;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public void setParticleCount(int particleCount) {
        this.particleCount = particleCount;
    }

    public boolean isLongDistance() {
        return longDistance;
    }

    public void setLongDistance(boolean longDistance) {
        this.longDistance = longDistance;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(long startDelay) {
        this.startDelay = startDelay;
    }

    public long getDelayBetweenEmits() {
        return delayBetweenEmits;
    }

    public void setDelayBetweenEmits(long delayBetweenEmits) {
        this.delayBetweenEmits = delayBetweenEmits;
    }

    public int getDuplicateValue() {
        return duplicateValue;
    }

    public void setDuplicateValue(int duplicateValue) {
        this.duplicateValue = duplicateValue;
    }

    public ParticleEffectBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(ParticleEffectBehavior behavior) {
        this.behavior = behavior;
    }

    public int getRepetitionsBeforeBehavior() {
        return repetitionsBeforeBehavior;
    }

    public void setRepetitionsBeforeBehavior(int repetitionsBeforeBehavior) {
        this.repetitionsBeforeBehavior = repetitionsBeforeBehavior;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public int getRotationDivisor() { return rotationDivisor; }

    public void setRotationDivisor(int rotationDivisor) { this.rotationDivisor = rotationDivisor; }
}