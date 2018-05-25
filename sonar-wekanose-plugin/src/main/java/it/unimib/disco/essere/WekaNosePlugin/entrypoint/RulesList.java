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
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule09;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule10;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule11;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule12;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule13;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule14;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule15;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule16;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule17;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule18;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule19;
import it.unimib.disco.essere.WekaNosePlugin.checks.Rule20;

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
				.add(Rule04.class).add(Rule05.class).add(Rule06.class).add(Rule07.class).add(Rule08.class)
				.add(Rule09.class).add(Rule10.class).add(Rule11.class).add(Rule12.class).add(Rule13.class)
				.add(Rule14.class).add(Rule15.class).add(Rule16.class).add(Rule17.class).add(Rule18.class)
				.add(Rule19.class).add(Rule20.class).build();
	}
}