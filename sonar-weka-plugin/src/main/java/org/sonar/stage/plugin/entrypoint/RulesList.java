package org.sonar.stage.plugin.entrypoint;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.stage.plugin.checks.DataClass;
import org.sonar.stage.plugin.checks.FeatureEnvy;
import org.sonar.stage.plugin.checks.GodClass;
import org.sonar.stage.plugin.checks.LongMethod;
import org.sonar.stage.plugin.checks.LongParameterList;
import org.sonar.stage.plugin.checks.SwitchStatement;
import org.sonar.stage.plugin.custom.checks.CustomRule1;
import org.sonar.stage.plugin.custom.checks.CustomRule2;

public final class RulesList {

	private RulesList() {

	}

	@SuppressWarnings("rawtypes")
	public static List<Class> getChecks() {

		return ImmutableList.<Class>builder().addAll(getJavaChecks()).build();
	}

	public static List<Class<? extends JavaCheck>> getJavaChecks() {

		return ImmutableList.<Class<? extends JavaCheck>>builder().add(LongParameterList.class)
				.add(FeatureEnvy.class)
				.add(LongMethod.class)
				.add(SwitchStatement.class)
				.add(DataClass.class)
				.add(GodClass.class)
				.add(CustomRule1.class)
				.add(CustomRule2.class)
				.build();
	}
}