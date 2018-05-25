package it.unimib.disco.essere.WekaNosePlugin.entrypoint;

import com.google.common.collect.ImmutableList;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule01;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule02;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule03;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule04;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule05;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule06;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule07;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule08;

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

		return ImmutableList.<Class<? extends JavaCheck>>builder().add(Rule01.class).add(Rule02.class).add(Rule03.class)
				.add(Rule04.class).add(Rule05.class).add(Rule06.class).add(Rule07.class).add(Rule08.class).build();
	}
}