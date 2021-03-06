package modfest.lacrimis.client.render.entity;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;

import modfest.lacrimis.entity.GhostEntity;

public class GhostEntityModel extends CompositeEntityModel<GhostEntity> {

    private final LinkedModelPart head;
    private final LinkedModelPart body;
    private final LinkedModelPart upperArmLeft;
    private final LinkedModelPart upperArmRight;
    private final LinkedModelPart lowerArmLeft;
    private final LinkedModelPart lowerArmRight;
    private final LinkedModelPart upperLegLeft;
    private final LinkedModelPart upperLegRight;
    private final LinkedModelPart lowerLegLeft;
    private final LinkedModelPart lowerLegRight;

    public GhostEntityModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        // the coordinates for lower* are simply wrong but I'm too lazy to spend
        // another couple days fixing createPart so oh well.
        // Correct coordinates are commented out...

        this.head = createPartFlipped(this, 0, 0,
                0.0f, 28.0f, 0.0f,
                8.0f, 8.0f, 8.0f,
                0.0f, 24.0f, 0.0f);
        this.body = createPartFlipped(this, 16, 16,
                0.0f, 18.0f, 0.0f,
                8.0f, 12.0f, 4.0f,
                0.0f, 24.0f, 0.0f);
        this.upperArmLeft = createPart(this, this.body, 32, 44,
                6.0f, 21.0f, 0.0f,
                4.0f, 6.0f, 4.0f,
                4.0f, 22.0f, 0.0f);
        this.upperArmRight = createPart(this, this.body, 40, 16,
                -6.0f, 21.0f, 0.0f,
                4.0f, 6.0f, 4.0f,
                -4.0f, 22.0f, 0.0f);
        this.lowerArmLeft = createPart(this, this.upperArmLeft, 32, 54,
                6.0f, -15.0f, 0.0f, // 6.0f, 15.0f, 0.0f
                4.0f, 6.0f, 4.0f,
                6.0f, -18.0f, 0.0f); // 6.0f, 18.0f, 0.0f
        this.lowerArmRight = createPart(this, this.upperArmRight, 40, 26,
                -6.0f, -15.0f, 0.0f, // -6.0f, 15.0f, 0.0f
                4.0f, 6.0f, 4.0f,
                -6.0f, -18.0f, 0.0f); // -6.0f, 18.0f, 0.0f
        this.upperLegLeft = createPart(this, this.body, 16, 44,
                2.0f, 9.0f, 0.0f,
                4.0f, 6.0f, 4.0f,
                2.0f, 12.0f, 0.0f);
        this.upperLegRight = createPart(this, this.body, 0, 16,
                -2.0f, 9.0f, 0.0f,
                4.0f, 6.0f, 4.0f,
                -2.0f, 12.0f, 0.0f);
        this.lowerLegLeft = createPart(this, this.upperLegLeft, 16, 54,
                2.0f, -3.0f, 0.0f, // 2.0f, 3.0f, 0.0f
                4.0f, 6.0f, 4.0f,
                2.0f, -6.0f, 0.0f); // 2.0f, 6.0f, 0.0f
        this.lowerLegRight = createPart(this, this.upperLegRight, 0, 26,
                -2.0f, -3.0f, 0.0f, // -2.0f, 3.0f, 0.0f
                4.0f, 6.0f, 4.0f,
                -2.0f, -6.0f, 0.0f); // -2.0f, 6.0f, 0.0f
        Direction.class.getEnumConstants();
    }

    private static LinkedModelPart createPart(Model model, int u, int v, float x, float y, float z, float sx, float sy, float sz, float px, float py, float pz) {
        LinkedModelPart part = new LinkedModelPart(model, u, v);
        part.addCuboid(x - sx / 2 - px, y - sy / 2 - py, z - sz / 2 - pz, sx, sy, sz);
        part.setPivot(px, py, pz);
        return part;
    }

    private static LinkedModelPart createPartFlipped(Model model, int u, int v, float x, float y, float z, float sx, float sy, float sz, float px, float py, float pz) {
        LinkedModelPart part = createPart(model, u, v,
                x, -y + 2 * py, z,
                sx, sy, sz,
                px, py, pz);
        part.flipped = true;
        return part;
    }

    private static LinkedModelPart createPart(Model model, LinkedModelPart parent, int u, int v, float x, float y, float z, float sx, float sy, float sz, float px, float py, float pz) {
        float ppx = parent.getTotalPivotX();
        float ppy = parent.getTotalPivotY();
        float ppz = parent.getTotalPivotZ();
        float x1 = x - ppx;
        float y1 = !parent.flipped ? y - ppy : -y + ppy;
        float z1 = !parent.flipped ? z - ppz : -z + ppz;
        float px1 = px - ppx;
        float py1 = !parent.flipped ? py - ppy : -py + ppy;
        float pz1 = !parent.flipped ? pz - ppz : -pz + ppz;

        LinkedModelPart part = createPart(model, u, v,
                x1, y1, z1,
                sx, sy, sz,
                px1, py1, pz1);
        parent.addChild(part);
        part.parent = parent;
        return part;
    }

    private static LinkedModelPart createPartFlipped(Model model, LinkedModelPart parent, int u, int v, float x, float y, float z, float sx, float sy, float sz, float px, float py, float pz) {
        LinkedModelPart part = createPart(model, parent, u, v,
                x, -y + 2 * py, z,
                sx, sy, sz,
                px, py, pz);
        part.flipped = true;
        return part;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return Arrays.asList(this.head, this.body);
    }

    @Override
    public void setAngles(GhostEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float yaw = limbDistance;

        Vec3d velocity = entity.getVelocity().multiply(2);
        Vec3d bodyRot = getRotationVector(0.0f, yaw);
        Vec3d bodyRotSide = getRotationVector(0.0f, yaw + 90.0f);

        float pitch = (float) velocity.dotProduct(bodyRot);
        float roll = (float) velocity.dotProduct(bodyRotSide);

        this.head.yaw = (float) (-headYaw * Math.PI / 180);
        this.head.pitch = (float) (headPitch * Math.PI / 180);
        this.body.yaw = (float) (-yaw * Math.PI / 180);
        this.body.pitch = pitch;
        this.body.roll = roll;
        this.upperArmLeft.yaw = 0.0f;
        this.upperArmLeft.pitch = pitch;
        this.upperArmLeft.roll = roll;
        this.upperArmRight.yaw = 0.0f;
        this.upperArmRight.pitch = pitch;
        this.upperArmRight.roll = roll;
        this.lowerArmLeft.yaw = 0.0f;
        this.lowerArmLeft.pitch = pitch;
        this.lowerArmLeft.roll = roll;
        this.lowerArmRight.yaw = 0.0f;
        this.lowerArmRight.pitch = pitch;
        this.lowerArmRight.roll = roll;
        this.upperLegLeft.yaw = 0.0f;
        this.upperLegLeft.pitch = pitch;
        this.upperLegLeft.roll = roll;
        this.upperLegRight.yaw = 0.0f;
        this.upperLegRight.pitch = pitch;
        this.upperLegRight.roll = roll;
        this.lowerLegLeft.yaw = 0.0f;
        this.lowerLegLeft.pitch = pitch;
        this.lowerLegLeft.roll = roll;
        this.lowerLegRight.yaw = 0.0f;
        this.lowerLegRight.pitch = pitch;
        this.lowerLegRight.roll = roll;

        // flip by 180°
        this.head.pitch += Math.PI;
        this.body.pitch += Math.PI;
//        this.upperArmLeft.pitch += Math.PI;
//        this.upperArmRight.pitch += Math.PI;
//        this.lowerArmLeft.pitch += Math.PI;
//        this.lowerArmRight.pitch += Math.PI;
//        this.upperLegLeft.pitch += Math.PI;
//        this.upperLegRight.pitch += Math.PI;
//        this.lowerLegLeft.pitch += Math.PI;
//        this.lowerLegRight.pitch += Math.PI;
    }

    private static Vec3d getRotationVector(float pitch, float yaw) {
        float f = (float) (pitch * Math.PI / 180);
        float g = (float) (-yaw * Math.PI / 180);
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d(i * j, -k, h * j);
    }

}
