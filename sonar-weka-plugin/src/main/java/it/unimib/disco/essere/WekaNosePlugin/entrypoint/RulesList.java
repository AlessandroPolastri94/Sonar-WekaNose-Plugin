package it.unimib.disco.essere.WekaNosePlugin.entrypoint;

import com.google.common.collect.ImmutableList;

import it.unimib.disco.essere.plugin.checks.CustomRule1;
import it.unimib.disco.essere.plugin.checks.CustomRule2;
import it.unimib.disco.essere.plugin.checks.CustomRule3;
import it.unimib.disco.essere.plugin.checks.CustomRule4;
import it.unimib.disco.essere.plugin.checks.CustomRule5;
import it.unimib.disco.essere.plugin.checks.CustomRule6;
import it.unimib.disco.essere.plugin.checks.CustomRule7;
import it.unimib.disco.essere.plugin.checks.CustomRule8;
import it.unimib.disco.essere.plugin.checks.CustomRule9;
import it.unimib.disco.essere.plugin.checks.DataClass;
import it.unimib.disco.essere.plugin.checks.FeatureEnvy;
import it.unimib.disco.essere.plugin.checks.GodClass;
import it.unimib.disco.essere.plugin.checks.LongMethod;
import it.unimib.disco.essere.plugin.checks.LongParameterList;
import it.unimib.disco.essere.plugin.checks.SwitchStatement;

import java.util.List;
import org.sonar.plugins.java.api.JavaCheck;

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
				.add(CustomRule9.class).build();
	}
}