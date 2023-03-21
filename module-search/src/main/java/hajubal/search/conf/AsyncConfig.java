package hajubal.search.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

	/** 기본 유지 쓰레드 개수 */
	private static final int TASK_CORE_POOL_SIZE = 10;

	/** 최대 쓰레드 개수 */
	private static final int TASK_MAX_POOL_SIZE = 20;

	/** MaxPoolSize 초과 요청에서 Thread 생성 요청시, 해당 요청을 Queue에 저장하는데 이때 최대 수용 가능한 Queue의 수 */
	private static final int TASK_QUEUE_CAPACITY = 100;

	/** MaxPoolSize가 모두 사용되고 종료하기까지 대기 시간 */
	private static final int TASK_KEEP_ALIVE_SECONDS = 60;

	/** 시스템 종료시 queue에 남아있는 작업을 처리 후 종료 Graceful shutdown */
	private static final boolean TASK_COMPLETE_ON_SHUTDOWN = true;

	private static final String TASK_NAME_PREFIX = "async-thread-";

	@Override
	@Bean(name = "threadPoolTaskExecutor")
	public Executor getAsyncExecutor() {
		final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(TASK_CORE_POOL_SIZE);
		taskExecutor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
		taskExecutor.setQueueCapacity(TASK_QUEUE_CAPACITY);
		taskExecutor.setKeepAliveSeconds(TASK_KEEP_ALIVE_SECONDS);
		taskExecutor.setWaitForTasksToCompleteOnShutdown(TASK_COMPLETE_ON_SHUTDOWN);
		taskExecutor.setThreadNamePrefix(TASK_NAME_PREFIX);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return asyncUncaughtExceptionHandler();
	}

	@Bean
	public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
		return (throwable, method, obj) -> log.error("AsyncExceptionHandler", throwable);
	}

}
