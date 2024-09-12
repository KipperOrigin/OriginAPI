package com.github.kipperorigin.originlib.particles;

import com.comphenix.protocol.wrappers.WrappedParticle;

public class ParticleEffect {

    private String particleEffectName;
    private WrappedParticle<?> wrappedParticle;
    private double initialX, initialY, initialZ;
    private float offsetX, offsetY, offsetZ;
    private String formulaX, formulaY, formulaZ;
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
                          double initialX, double initialY, double initialZ,
                          float offsetX, float offsetY, float offsetZ,
                          String formulaX, String formulaY, String formulaZ,
                          int particleCount, boolean longDistance,
                          long startDelay, long delayBetweenEmits, int duplicateValue,
                          ParticleEffectBehavior behavior, int repetitionsBeforeBehavior,
                          int totalSteps, int rotationDivisor) {
        this.particleEffectName = particleEffectName;
        this.wrappedParticle = wrappedParticle;
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialZ = initialZ;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.formulaX = formulaX;
        this.formulaY = formulaY;
        this.formulaZ = formulaZ;
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

    public double getInitialX() {
        return initialX;
    }

    public void setInitialX(double initialX) {
        this.initialX = initialX;
    }

    public double getInitialY() {
        return initialY;
    }

    public void setInitialY(double initialY) {
        this.initialY = initialY;
    }

    public double getInitialZ() {
        return initialZ;
    }

    public void setInitialZ(double initialZ) {
        this.initialZ = initialZ;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
    }

    public String getFormulaX() {
        return formulaX;
    }

    public void setFormulaX(String formulaX) {
        this.formulaX = formulaX;
    }

    public String getFormulaY() {
        return formulaY;
    }

    public void setFormulaY(String formulaY) {
        this.formulaY = formulaY;
    }

    public String getFormulaZ() {
        return formulaZ;
    }

    public void setFormulaZ(String formulaZ) {
        this.formulaZ = formulaZ;
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