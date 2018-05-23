package org.sonar.stage.plugin.entrypoint;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.stage.plugin.checks.CustomRule1;
import org.sonar.stage.plugin.checks.CustomRule10;
import org.sonar.stage.plugin.checks.CustomRule2;
import org.sonar.stage.plugin.checks.CustomRule3;
import org.sonar.stage.plugin.checks.CustomRule4;
import org.sonar.stage.plugin.checks.CustomRule5;
import org.sonar.stage.plugin.checks.CustomRule6;
import org.sonar.stage.plugin.checks.CustomRule7;
import org.sonar.stage.plugin.checks.CustomRule8;
import org.sonar.stage.plugin.checks.CustomRule9;
import org.sonar.stage.plugin.checks.DataClass;
import org.sonar.stage.plugin.checks.FeatureEnvy;
import org.sonar.stage.plugin.checks.GodClass;
import org.sonar.stage.plugin.checks.LongMethod;
import org.sonar.stage.plugin.checks.LongParameterList;
import org.sonar.stage.plugin.checks.SwitchStatement;

public final class RulesList {

	private RulesList() {

	}

	@SuppressWarnings("rawtypes")
	public static List<Class> getChecks() {

		return ImmutableList.<Class>builder().addAll(getJavaChecks()).build();
	}

	public static List<Class<? extends JavaCheck>> getJavaChecks() {

		return ImmutableList.<Class<? extends JavaCheck>>builder().add(LongParameterList.class).add(FeatureEnvy.class)
				.add(LongMethod.class).add(SwitchStatement.class).add(DataClass.class).add(GodClass.class)
				.add(CustomRule1.class).add(CustomRule2.class).add(CustomRule3.class).add(CustomRule4.class)
				.add(CustomRule5.class).add(CustomRule6.class).add(CustomRule7.class).add(CustomRule8.class)
				.add(CustomRule9.class).add(CustomRule10.class).build();
	}
}