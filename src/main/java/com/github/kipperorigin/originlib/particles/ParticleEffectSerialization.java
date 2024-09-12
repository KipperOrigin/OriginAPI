package com.github.kipperorigin.originlib.particles;

import com.comphenix.protocol.wrappers.WrappedParticle;
import com.google.gson.*;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class ParticleEffectSerialization {
    public static class ParticleEffectSerializer implements JsonSerializer<ParticleEffect> {
        @Override
        public JsonElement serialize(ParticleEffect effect, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("particleEffectName", effect.getParticleEffectName());
            jsonObject.add("particleData", serializeParticleData(effect.getWrappedParticle()));
            jsonObject.addProperty("particleType", effect.getWrappedParticle().getParticle().name());
            jsonObject.addProperty("initialX", effect.getInitialX());
            jsonObject.addProperty("initialY", effect.getInitialY());
            jsonObject.addProperty("initialZ", effect.getInitialZ());
            jsonObject.addProperty("offsetX", effect.getOffsetX());
            jsonObject.addProperty("offsetY", effect.getOffsetY());
            jsonObject.addProperty("offsetZ", effect.getOffsetZ());
            jsonObject.addProperty("formulaX", effect.getFormulaX());
            jsonObject.addProperty("formulaY", effect.getFormulaY());
            jsonObject.addProperty("formulaZ", effect.getFormulaZ());
            jsonObject.addProperty("particleCount", effect.getParticleCount());
            jsonObject.addProperty("longDistance", effect.isLongDistance());
            jsonObject.addProperty("startDelay", effect.getStartDelay());
            jsonObject.addProperty("delayBetweenEmits", effect.getDelayBetweenEmits());
            jsonObject.addProperty("duplicateValue", effect.getDuplicateValue());
            jsonObject.addProperty("behavior", effect.getBehavior().name());
            jsonObject.addProperty("repetitionsBeforeBehavior", effect.getRepetitionsBeforeBehavior());
            jsonObject.addProperty("totalSteps", effect.getTotalSteps());
            jsonObject.addProperty("rotationDivisor", effect.getRotationDivisor());
            return jsonObject;
        }
    }

    public static class ParticleEffectDeserializer implements JsonDeserializer<ParticleEffect> {
        @Override
        public ParticleEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            String particleEffectName = jsonObject.get("particleEffectName").getAsString();
            Particle particleType = Particle.valueOf(jsonObject.get("particleType").getAsString());
            Object particleData = jsonObject.has("particleData") ? deserializeParticleData(jsonObject.get("particleData").getAsJsonObject(), particleType) : null;
            WrappedParticle<?> wrappedParticle = WrappedParticle.create(particleType, particleData);
            double initialX = jsonObject.get("initialX").getAsDouble();
            double initialY = jsonObject.get("initialY").getAsDouble();
            double initialZ = jsonObject.get("initialZ").getAsDouble();
            float offsetX = jsonObject.get("offsetX").getAsFloat();
            float offsetY = jsonObject.get("offsetY").getAsFloat();
            float offsetZ = jsonObject.get("offsetZ").getAsFloat();
            String formulaX = jsonObject.get("formulaX").getAsString();
            String formulaY = jsonObject.get("formulaY").getAsString();
            String formulaZ = jsonObject.get("formulaZ").getAsString();
            int particleCount = jsonObject.get("particleCount").getAsInt();
            boolean longDistance = jsonObject.get("longDistance").getAsBoolean();
            long startDelay = jsonObject.get("startDelay").getAsLong();
            long delayBetweenEmits = jsonObject.get("delayBetweenEmits").getAsLong();
            int duplicateValue = jsonObject.get("duplicateValue").getAsInt();
            ParticleEffectBehavior behavior = ParticleEffectBehavior.valueOf(jsonObject.get("behavior").getAsString());
            int repetitionsBeforeBehavior = jsonObject.get("repetitionsBeforeBehavior").getAsInt();
            int totalSteps = jsonObject.get("totalSteps").getAsInt();
            int rotationDivisor = jsonObject.get("rotationDivisor").getAsInt();

            return new ParticleEffect(
                    particleEffectName, wrappedParticle,
                    initialX, initialY, initialZ,
                    offsetX, offsetY, offsetZ,
                    formulaX, formulaY, formulaZ,
                    particleCount, longDistance,
                    startDelay, delayBetweenEmits, duplicateValue,
                    behavior, repetitionsBeforeBehavior,
                    totalSteps, rotationDivisor
            );
        }
    }

    private static JsonElement serializeParticleData(WrappedParticle<?> wrappedParticle) {
        Object data = wrappedParticle.getData();
        if (data == null) {
            return JsonNull.INSTANCE;
        }

        JsonObject dataObject = new JsonObject();
        dataObject.addProperty("type", data.getClass().getSimpleName());

        if (data instanceof BlockData) {
            dataObject.addProperty("value", ((BlockData) data).getAsString());
        } else if (data instanceof Particle.DustOptions) {
            if (data instanceof Particle.DustTransition) {
                Particle.DustTransition dustTransition = (Particle.DustTransition) data;
                dataObject.addProperty("fromColor", dustTransition.getColor().asRGB());
                dataObject.addProperty("toColor", dustTransition.getToColor().asRGB());
                dataObject.addProperty("size", dustTransition.getSize());
            } else {
                Particle.DustOptions dustOptions = (Particle.DustOptions) data;
                dataObject.addProperty("color", dustOptions.getColor().asRGB());
                dataObject.addProperty("size", dustOptions.getSize());
            }
        } else if (data instanceof Color) {
            dataObject.addProperty("value", ((Color) data).asRGB());
        } else if (data instanceof ItemStack) {
            // Serialize ItemStack (you may need a more comprehensive serialization method)
            dataObject.addProperty("value", ((ItemStack) data).getType().name());
        } else if (data instanceof Float || data instanceof Integer) {
            dataObject.addProperty("value", data.toString());
        } else if (data instanceof Vibration) {
            Vibration vibration = (Vibration) data;
            JsonObject vibrationObject = new JsonObject();
            vibrationObject.add("origin", serializeLocation(vibration.getOrigin()));
            vibrationObject.add("destination", serializeVibrationDestination(vibration.getDestination()));
            vibrationObject.addProperty("arrivalTime", vibration.getArrivalTime());
            dataObject.add("value", vibrationObject);
        }

        return dataObject;
    }

    private static Object deserializeParticleData(JsonObject dataObject, Particle particleType) {
        String type = dataObject.get("type").getAsString();
        JsonElement valueElement = dataObject.get("value");

        switch (type) {
            case "BlockData":
                return Bukkit.createBlockData(valueElement.getAsString());
            case "DustOptions":
                Color color = Color.fromRGB(dataObject.get("color").getAsInt());
                float size = dataObject.get("size").getAsFloat();
                return new Particle.DustOptions(color, size);
            case "DustTransition":
                Color fromColor = Color.fromRGB(dataObject.get("fromColor").getAsInt());
                Color toColor = Color.fromRGB(dataObject.get("toColor").getAsInt());
                float transitionSize = dataObject.get("size").getAsFloat();
                return new Particle.DustTransition(fromColor, toColor, transitionSize);
            case "Color":
                return Color.fromRGB(valueElement.getAsInt());
            case "ItemStack":
                return new ItemStack(Material.valueOf(valueElement.getAsString()));
            case "Float":
                return valueElement.getAsFloat();
            case "Integer":
                return valueElement.getAsInt();
            case "Vibration":
                JsonObject vibrationObject = valueElement.getAsJsonObject();
                Location origin = deserializeLocation(vibrationObject.get("origin").getAsJsonObject());
                Vibration.Destination destination = deserializeVibrationDestination(vibrationObject.get("destination").getAsJsonObject());
                int arrivalTime = vibrationObject.get("arrivalTime").getAsInt();
                return new Vibration(origin, destination, arrivalTime);
            default:
                return null;
        }
    }

    private static JsonObject serializeLocation(Location location) {
        JsonObject locationObject = new JsonObject();
        locationObject.addProperty("world", location.getWorld().getName());
        locationObject.addProperty("x", location.getX());
        locationObject.addProperty("y", location.getY());
        locationObject.addProperty("z", location.getZ());
        return locationObject;
    }

    private static JsonObject serializeVibrationDestination(Vibration.Destination destination) {
        JsonObject destinationObject = new JsonObject();
        if (destination instanceof Vibration.Destination.BlockDestination) {
            Vibration.Destination.BlockDestination blockDest = (Vibration.Destination.BlockDestination) destination;
            destinationObject.addProperty("type", "BlockDestination");
            destinationObject.add("location", serializeLocation(blockDest.getLocation()));
        } else if (destination instanceof Vibration.Destination.EntityDestination) {
            Vibration.Destination.EntityDestination entityDest = (Vibration.Destination.EntityDestination) destination;
            destinationObject.addProperty("type", "EntityDestination");
            destinationObject.addProperty("entityId", entityDest.getEntity().getEntityId());
        }
        return destinationObject;
    }

    private static Location deserializeLocation(JsonObject locationObject) {
        String worldName = locationObject.get("world").getAsString();
        double x = locationObject.get("x").getAsDouble();
        double y = locationObject.get("y").getAsDouble();
        double z = locationObject.get("z").getAsDouble();
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    private static Vibration.Destination deserializeVibrationDestination(JsonObject destinationObject) {
        String type = destinationObject.get("type").getAsString();
        if ("BlockDestination".equals(type)) {
            Location location = deserializeLocation(destinationObject.get("location").getAsJsonObject());
            return new Vibration.Destination.BlockDestination(location);
        } else if ("EntityDestination".equals(type)) {
            int entityId = destinationObject.get("entityId").getAsInt();
            // Note: You'll need to implement a way to get the entity from the entityId
            // This might involve storing a reference to the world or server
            Entity entity = getEntityById(entityId);
            return new Vibration.Destination.EntityDestination(entity);
        }
        throw new JsonParseException("Unknown Vibration.Destination type: " + type);
    }

    // You'll need to implement this method to get an entity from its ID
    private static Entity getEntityById(int entityId) {
        for (World world: Bukkit.getWorlds()) {
            for (Entity entity: world.getEntities()) {
                if (entityId == entity.getEntityId()) return entity;
            }
        }
        return null;
    }
}
