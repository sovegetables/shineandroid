package com.sovegetables.permission

import java.util.*
import kotlin.math.abs

internal class Util {

    companion object{

        private val codeSet = hashSetOf<Int>()
        private val DEFAULT_CODE = abs(UUID.randomUUID().hashCode()) % 100000

        fun createRequestCode(): Int{
            var i = DEFAULT_CODE
            for(j in 1..1000){
                i = abs(UUID.randomUUID().hashCode()) % 100000
                if (i and -0x10000 != 0) {
                    continue
                }
                if(!codeSet.contains(i)){ //必须是低16位
                    codeSet.add(i)
                    return i
                }
            }
            return i
        }
    }
}