package com.distraction.fs2

class AnimationSet {

    val set = hashMapOf<String, Animation>()
    var currentAnimationKey: String? = null
    lateinit var currentAnimation: Animation

    fun addAnimation(key: String, value: Animation) {
        set[key] = value
    }

    fun setAnimation(key: String) {
        if (key == currentAnimationKey) {
            return
        }
        set[key]?.let {
            currentAnimationKey = key
            currentAnimation = it
            currentAnimation.reset()
        } ?: run {
            throw IllegalArgumentException()
        }
    }

    fun update(dt: Float) = currentAnimation.update(dt)

    fun getImage() = currentAnimation.getImage()

}