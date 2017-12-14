package gln.texture

import gli_.gl
import gli_.gli
import glm_.BYTES
import glm_.vec2.Vec2i
import glm_.vec3.Vec3i
import gln.buf
import gln.bufAd
import gln.get
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL42
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import java.nio.IntBuffer
import kotlin.properties.Delegates


var textureName: IntBuffer by Delegates.notNull()

operator fun Int.plus(enum: Enum<*>) = plus(enum.ordinal)

inline fun glBindTexture(target: Int, texture: Enum<*>) = GL11.glBindTexture(target, textureName[texture])
inline fun glBindTexture(target: Int, texture: IntBuffer) = GL11.glBindTexture(target, texture[0])

inline fun glBindTexture(target: gli_.gl.Target, texture: Int) = GL11.glBindTexture(target.i, texture)
inline fun glBindTexture(target: gli_.gl.Target, texture: Enum<*>) = GL11.glBindTexture(target.i, textureName[texture])
inline fun glBindTexture(target: gli_.gl.Target, texture: IntBuffer) = GL11.glBindTexture(target.i, texture[0])

inline fun glBindTexture(target: Int) = GL11.glBindTexture(target, 0)

inline fun glTexParameteri(target: gli_.gl.Target, pName: Int, param: Int) = GL11.glTexParameteri(target.i, pName, param)
inline fun glTexParameteriv(target: gli_.gl.Target, pName: Int, param: gli_.gl.Swizzles) {
    buf.putInt(0, param[0].i).putInt(Int.BYTES, param[1].i).putInt(Int.BYTES * 2, param[2].i).putInt(Int.BYTES * 3, param[3].i)
    GL11.nglTexParameteriv(target.i, pName, bufAd)
}

inline fun glTexImage2D(level: Int, internalformat: Int, width: Int, height: Int, format: Int, type: Int, pixels: ByteBuffer)
        = GL11.nglTexImage2D(GL11.GL_TEXTURE_2D, level, internalformat, width, height, 0, format, type, MemoryUtil.memAddress(pixels, pixels.position()))
inline fun glTexImage2D(internalformat: Int, width: Int, height: Int, format: Int, type: Int, pixels: ByteBuffer)
        = glTexImage2D(0, internalformat, width, height, format, type, pixels)

inline fun glTexStorage2D(target: Int, internalFormat: Int, size: Vec2i) = GL42.glTexStorage2D(target, 1, internalFormat, size.x, size.y)
//inline fun glTexStorage2D(target: gli_.gl.Target, internalFormat: Int, size: Vec2i) = GL42.glTexStorage2D(target.i, 1, internalFormat, size.x, size.y) TODO ?
inline fun glTexStorage2D(target: gli_.gl.Target, internalFormat: gli_.gl.InternalFormat, size: Vec3i) = glTexStorage2D(target, 1, internalFormat, size.x, size.y)
inline fun glTexStorage2D(target: gli_.gl.Target, levels: Int, internalFormat: gli_.gl.InternalFormat, size: Vec3i) = glTexStorage2D(target, levels, internalFormat, size.x, size.y)
inline fun glTexStorage2D(target: gli_.gl.Target, levels: Int, internalFormat: gli_.gl.InternalFormat, width: Int, height: Int) = GL42.glTexStorage2D(target.i, levels, internalFormat.i, width, height)

inline fun glCompressedTexSubImage2D(target: gli_.gl.Target, level: Int, extent: Vec3i, format: gli_.gl.InternalFormat, data: ByteBuffer) =
        glCompressedTexSubImage2D(target, level, 0, 0, extent.x, extent.y, format, data)
inline fun glCompressedTexSubImage2D(target: gli_.gl.Target, level: Int, xOffset: Int, yOffset: Int, extent: Vec3i, format: gli_.gl.InternalFormat, data: ByteBuffer) =
        glCompressedTexSubImage2D(target, level, xOffset, yOffset, extent.x, extent.y, format, data)
inline fun glCompressedTexSubImage2D(target: gli_.gl.Target, level: Int, xOffset: Int, yOffset: Int, width: Int, height: Int, format: gli_.gl.InternalFormat, data: ByteBuffer) =
        GL13.glCompressedTexSubImage2D(target.i, level, xOffset, yOffset, width, height, format.i, data)