package com.okay.router.compiler;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class ProcessorConfigManager {

	private Elements elementUtils = null;
	private Filer filer = null;
	private Messager messager = null;
	private Types typeUtils = null;


	private ProcessorConfigManager() { }

	public static ProcessorConfigManager instance() {
		return SingletonHolder.configManager;
	}

	private static class SingletonHolder {
		private static final ProcessorConfigManager configManager = new ProcessorConfigManager();
	}

	public void init (ProcessingEnvironment environment) {
		setElementUtils(environment.getElementUtils());
		setFiler(environment.getFiler());
		setMessager(environment.getMessager());
		setTypeUtils(environment.getTypeUtils());
	}
	

	public Elements getElementUtils() {
		return elementUtils;
	}

	public void setElementUtils(Elements elementUtils) {
		this.elementUtils = elementUtils;
	}

	public Filer getFiler() {
		return filer;
	}

	public void setFiler(Filer filer) {
		this.filer = filer;
	}

	public Messager getMessager() {
		return messager;
	}

	public void setMessager(Messager messager) {
		this.messager = messager;
	}

	public Types getTypeUtils() {
		return typeUtils;
	}

	public void setTypeUtils(Types typeUtils) {
		this.typeUtils = typeUtils;
	}

}
