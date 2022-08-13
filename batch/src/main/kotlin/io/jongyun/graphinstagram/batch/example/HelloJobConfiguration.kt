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
class HelloJobConfiguration(
    val jobBuilderFactory: JobBuilderFactory, val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun helloJob(): Job {
        return jobBuilderFactory.get("helloJob").start(helloStep1()).next(helloStep2()).build()
    }

    @Bean
    fun helloStep1(): Step {
        return stepBuilderFactory.get("helloStep1").tasklet { _: StepContribution, _: ChunkContext ->
                println("hello step1")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun helloStep2(): Step {
        return stepBuilderFactory.get("helloStep2").tasklet { _: StepContribution, _: ChunkContext ->
                println("hello step2")
                RepeatStatus.FINISHED
            }.build()
    }

}