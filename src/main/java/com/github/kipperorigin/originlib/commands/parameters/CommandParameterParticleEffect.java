package com.github.kipperorigin.originlib.commands.parameters;

import com.github.kipperorigin.originlib.particles.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandParameterParticleEffect extends CommandParameter {

    private List<ParticleEffect> effects;
    public CommandParameterParticleEffect(Map<String, ParticleEffect> effectMap) {
        super("not a valid list!");
        effects = new ArrayList<>();
        for (Map.Entry<String, ParticleEffect> entry : effectMap.entrySet()) {
            effects.add(entry.getValue());
        }
    }

    @Override
    public boolean checkArgument(String argument) {
        for (ParticleEffect effect: effects) {
            if (effect.getParticleEffectName().equalsIgnoreCase(argument)) return true;
        }
        return false;
    }

    @Override
    public Object asObject(String argument) {
        List<String> tabCompletes = new ArrayList<>();
        for (ParticleEffect effect: effects) {
            if (effect.getParticleEffectName().equalsIgnoreCase(argument)) return effect;
        }
        return null;
    }

    @Override
    public List<String> getTabCompletes() {
        List<String> tabCompletes = new ArrayList<>();
        for (ParticleEffect effect: effects) {
            tabCompletes.add(effect.getParticleEffectName());
        }
        return tabCompletes;
    }
}
