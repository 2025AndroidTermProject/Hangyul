package com.android.hangyul.util

import kotlin.random.Random

class EmotionCommentProvider {
    companion object {
        private val emotionComments = mapOf(
            "공포" to listOf(
                "두려움은 용기가 될 수 있어요. \n지금의 공포를 극복하면 더욱 강해질 거예요.",
                "무서운 일이 있었군요. \n하지만 당신은 혼자가 아니에요. 제가 곁에 있을게요.",
                "공포는 일시적인 감정이에요. \n곧 평온이 찾아올 거예요. 심호흡을 해보세요."
            ),
            "놀람" to listOf(
                "놀라운 일이 있었군요! \n이런 순간들이 인생을 더 특별하게 만들어요.",
                "예상치 못한 일이 있었지만,\n 그것도 좋은 경험이 될 거예요.",
                "놀라운 일이 있었지만,\n 당신은 잘 대처할 수 있어요."
            ),
            "분노" to listOf(
                "화가 나는 일이 있었군요. 하지만 그 감정도 당연한 거예요.\n 천천히 진정해보세요.",
                "지금은 화가 날 수 있어요. \n하지만 그 감정도 당신의 일부예요. 인정하고 받아들이는 것도 중요해요.",
                "화가 나는 일이 있었지만,\n 당신은 그 감정을 잘 다룰 수 있어요. 잠시 숨을 깊게 들이마셔보세요."
            ),
            "슬픔" to listOf(
                "힘든 시간이 지나면 반드시 좋은 일이 찾아올 거예요. \n지금은 충분히 슬퍼도 괜찮아요.",
                "당신의 감정을 이해해요. \n하지만 이 슬픔도 언젠가는 좋은 추억이 될 거예요.",
                "지금은 어려울 수 있지만, \n당신은 충분히 강해요. 곧 좋은 일이 찾아올 거예요."
            ),
            "중립" to listOf(
                "평온한 하루를 보내셨네요. \n이런 날들도 소중한 추억이 될 거예요.",
                "차분한 하루를 보내셨군요. \n내일은 더 특별한 하루가 될 거예요.",
                "평화로운 하루를 보내셨네요. \n이런 순간들이 모여 특별한 추억이 될 거예요."
            ),
            "행복" to listOf(
                "오늘도 행복한 하루였네요! \n내일도 좋은 일이 가득할 거예요.",
                "기분 좋은 하루를 보내셨군요. \n이런 순간들이 모여 더 행복한 나날이 될 거예요.",
                "당신의 행복이 저도 기분 좋게 만들어요.\n 앞으로도 이런 순간들이 많았으면 좋겠어요."
            ),
            "혐오" to listOf(
                "불편한 일이 있었군요. 하지만 그 감정도 당연한 거예요. \n천천히 마음을 가라앉혀보세요.",
                "싫은 일이 있었지만, 그것도 지나갈 거예요. \n지금은 충분히 쉬어도 좋아요.",
                "불편한 감정이 들었지만, 당신은 그 감정을 잘 다룰 수 있어요."
            )
        )

        fun getRandomComment(emotion: String): String {
            val commentList = emotionComments[emotion] ?: listOf("오늘 하루도 수고하셨어요.")
            return commentList[Random.nextInt(commentList.size)]
        }
    }
}
fun main(){
    var comfortMessage = EmotionCommentProvider.getRandomComment("행복")
    println(comfortMessage)
    comfortMessage = EmotionCommentProvider.getRandomComment("행복")
    println(comfortMessage)
    comfortMessage = EmotionCommentProvider.getRandomComment("행복")
    println(comfortMessage)
    comfortMessage = EmotionCommentProvider.getRandomComment("행복")
    println(comfortMessage)
    comfortMessage = EmotionCommentProvider.getRandomComment("행복")
    println(comfortMessage)
}