package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoin
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

//호출 api에 따른 Use Case
class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    //데이터 흐름에서 특정 코인 리스트에 대한 값 방출
    operator fun invoke() : Flow<Resource<List<Coin>>> = flow {
        try {
            //api 통신 결과에 대한 호출 및 예외처리
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured!"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server!"))
        }
    }
}