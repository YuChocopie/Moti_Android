package com.ahobsu.moti.data.source.answer

import com.ahobsu.moti.data.dto.AnswersWeekResponse
import com.ahobsu.moti.data.dto.BaseData
import com.ahobsu.moti.domain.entity.Answer
import io.reactivex.rxjava3.core.Single

interface AnswerDataSource {
    fun postAnswer(answer: Answer): Single<BaseData<Unit>>
    fun getAnswersWeek(): Single<BaseData<AnswersWeekResponse>>
}