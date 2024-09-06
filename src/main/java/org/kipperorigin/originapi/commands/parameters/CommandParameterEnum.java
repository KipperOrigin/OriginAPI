package org.kipperorigin.originapi.commands.parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParameterEnum extends CommandParameter {

    Class<? extends Enum> enumType;
    List<Object> ignoredEnumValues;
    public CommandParameterEnum(Class<? extends Enum> enumType) {
        super("must be a(n) &2" + enumType.getSimpleName() + "&c!");
        this.enumType = enumType;
        ignoredEnumValues = new ArrayList<>();
    }

    public CommandParameterEnum(Class<? extends Enum> enumType,Object... ignoredEnumValues) {
        super("must be a(n) &2" + enumType.getSimpleName() + "&c!");
        this.enumType = enumType;
        this.ignoredEnumValues = Arrays.asList(ignoredEnumValues);
    }

    @Override
    public boolean checkArgument(String argument) {
        try {
            Enum.valueOf(enumType, argument.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return false;
        }

        for (Object enumValue:enumType.getEnumConstants()) {
            if (ignoredEnumValues.contains(enumValue)) return false; //Returns false it ignored enum values contains enum.
        }
        return true;
    }

    @Override
    public Object asObject(String argument) {
        try {
            return Enum.valueOf(enumType, argument.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public List<String> getTabCompletes() {
        List<String> tabCompletes = new ArrayList<>();

        for (Object enumValue:enumType.getEnumConstants()) {
            if (!ignoredEnumValues.contains(enumValue)) tabCompletes.add(enumValue.toString()); //Will only add if ignored enum values does not contain;
        }

        return tabCompletes;
    }

    public Class<? extends  Enum> getEnumType() {
        return enumType;
    }
}
