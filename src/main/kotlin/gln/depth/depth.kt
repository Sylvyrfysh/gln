package gln.depth

import gln.texture.CompareFunc
import kool.stak
import org.lwjgl.opengl.*

inline fun depth(block: ObjectDepth.() -> Unit) = ObjectDepth.block()

object ObjectDepth {

    var test: Boolean
        get() = GL11C.glIsEnabled(GL11C.GL_DEPTH_TEST)
        set(value) = when {
            value -> GL11C.glEnable(GL11C.GL_DEPTH_TEST)
            else -> GL11C.glDisable(GL11C.GL_DEPTH_TEST)
        }

    var mask: Boolean
        get() = GL11C.glGetBoolean(GL11C.GL_DEPTH_WRITEMASK)
        set(value) = GL11C.glDepthMask(value)

    var func: CompareFunc
        get() = CompareFunc(GL11C.glGetInteger(GL11C.GL_DEPTH_FUNC))
        set(value) = GL11C.glDepthFunc(func.i)

    var range: ClosedFloatingPointRange<Double>
        get() = stak {
            val d = it.callocDouble(2)
            GL11C.glGetDoublev(GL11C.GL_DEPTH_RANGE, d)
            d[0]..d[1]
        }
        set(value) = GL11C.glDepthRange(value.start, value.endInclusive)

    var rangef: ClosedFloatingPointRange<Float>
        get() = stak {
            val f = it.callocFloat(2)
            GL11C.glGetFloatv(GL11C.GL_DEPTH_RANGE, f)
            f[0]..f[1]
        }
        set(value) = GL41C.glDepthRangef(value.start, value.endInclusive)

    var clamp: Boolean
        get() = GL11C.glIsEnabled(GL32C.GL_DEPTH_CLAMP)
        set(value) = when {
            value -> GL11C.glEnable(GL32.GL_DEPTH_CLAMP)
            else -> GL11C.glDisable(GL32.GL_DEPTH_CLAMP)
        }
}