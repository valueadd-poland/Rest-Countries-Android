package pl.valueadd.restcountries.domain.mapper

import pl.valueadd.restcountries.domain.model.ExampleModel
import pl.valueadd.restcountries.network.dto.example.ExampleDto
import pl.valueadd.restcountries.persistence.entity.ExampleEntity
import org.mapstruct.Mapper

@Mapper
interface ExampleMapper {

    fun mapExampleDtoToEntity(model: ExampleDto): ExampleEntity

    fun mapExampleEntityToModel(model: ExampleEntity): ExampleModel

    fun mapExampleDtosToEntities(model: List<ExampleDto>): List<ExampleEntity>

    fun mapExampleEntitiesToModels(model: List<ExampleEntity>): List<ExampleModel>
}
