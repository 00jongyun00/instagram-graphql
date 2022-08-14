package io.jongyun.graphinstagram.batch.example

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobInstanceConfiguration(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("helloStep1")
            .tasklet { stepContribution: StepContribution, chunkContext: ChunkContext ->
                val jobParametersByChunk = chunkContext.stepContext.jobParameters
                val jobParameters = stepContribution.stepExecution.jobExecution.jobParameters
                val name = jobParameters.getString("name")
                val id = jobParameters.getLong("id")
                val date = jobParameters.getDate("date")
                val weight = jobParameters.getDouble("weight")
                println("name: $name, id: $id, date: $date, weight: $weight")

                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory.get("helloStep2").tasklet { _: StepContribution, _: ChunkContext ->
            println("hello step2")
            RepeatStatus.FINISHED
        }.build()
    }
}
