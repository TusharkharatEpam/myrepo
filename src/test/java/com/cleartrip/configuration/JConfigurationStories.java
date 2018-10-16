package com.cleartrip.configuration;

	import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.RethrowingFailure;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.AbsolutePathCalculator;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.MarkUnmatchedStepsAsPending;
import org.jbehave.core.steps.PrintStreamStepMonitor;
import org.jbehave.core.steps.StepFinder;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import com.thoughtworks.paranamer.NullParanamer;


	public class JConfigurationStories extends JUnitStories {

		
	    private Configuration configuration;

	    public JConfigurationStories() {
		super();
		configuration = new MostUsefulConfiguration();
		configuration.useFailureStrategy(new RethrowingFailure());
		configuration.usePendingStepStrategy(new FailingUponPendingStep());
		Keywords keywords = new LocalizedKeywords(Locale.ENGLISH);
		configuration.useKeywords(keywords);
		configuration.useParanamer(new NullParanamer());
		configuration.usePathCalculator(new AbsolutePathCalculator());
		configuration.useStepCollector(new MarkUnmatchedStepsAsPending());
		configuration.useStepFinder(new StepFinder());
		configuration.useStepMonitor(new PrintStreamStepMonitor());
		configuration.useStepPatternParser(new RegexPrefixCapturingPatternParser());
		configuration.useStoryControls(new StoryControls());
		configuration.useStoryLoader(new LoadFromClasspath());
		configuration.useStoryParser(new RegexStoryParser(configuration.keywords()));
		configuration.useStoryPathResolver(new UnderscoredCamelCaseResolver());
		configuration.useStoryReporterBuilder(new StoryReporterBuilder()
		        .withFormats(Format.STATS).withFailureTrace(true));
		EmbedderControls embedderControls = configuredEmbedder().embedderControls();
		embedderControls.doBatch(true);
		embedderControls.doIgnoreFailureInStories(true);
	    }

	    @Override
	    protected List<String> storyPaths() {
		StoryFinder storyFinder = new StoryFinder();
		String storyLocation = System.getProperty("user.dir")
		        + "/src/test/resources";
		List<String> storyPaths = storyFinder.findPaths(storyLocation,
		        Arrays.asList("**/searchFlight_OneWayRestService.story"), Arrays.asList(""));
		return storyPaths;
		// return Arrays.asList("stories/Sqr.story");
	    }

	    @Override
	    public Configuration configuration() {
		return configuration;

	    }

	    @Override
	    public InjectableStepsFactory stepsFactory() {
	    	
	        	 AbstractApplicationContext  context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
	    	 	 return new SpringStepsFactory(configuration, context);
		
	    }

	


}
