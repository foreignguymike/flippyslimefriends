package com.distraction.fs2

class AnimationSet {

    val set = hashMapOf<String, Animation>()
    var currentAnimationKey: String? = null
    var currentAnimation: Animation? = null

    fun addAnimation(key: String, value: Animation) {
        set[key] = value
    }

    fun setAnimation(key: String) {
        if (key == currentAnimationKey) {
            return
        }
        currentAnimationKey = key
        currentAnimation = set[key]
        currentAnimation?.reset()
    }

    fun update(dt: Float) = currentAnimation?.update(dt)

    fun getImage() = currentAnimation?.getImage()

}