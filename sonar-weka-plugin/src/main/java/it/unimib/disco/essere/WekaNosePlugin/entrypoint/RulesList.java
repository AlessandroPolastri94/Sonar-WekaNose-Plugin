package it.unimib.disco.essere.WekaNosePlugin.entrypoint;

import com.google.common.collect.ImmutableList;

import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule1;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule2;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule3;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule4;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule5;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule6;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule7;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule8;
import it.unimib.disco.essere.WekaNosePlugin.check.CustomRule9;
import it.unimib.disco.essere.WekaNosePlugin.check.DataClass;
import it.unimib.disco.essere.WekaNosePlugin.check.FeatureEnvy;
import it.unimib.disco.essere.WekaNosePlugin.check.GodClass;
import it.unimib.disco.essere.WekaNosePlugin.check.LongMethod;
import it.unimib.disco.essere.WekaNosePlugin.check.LongParameterList;
import it.unimib.disco.essere.WekaNosePlugin.check.SwitchStatement;

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