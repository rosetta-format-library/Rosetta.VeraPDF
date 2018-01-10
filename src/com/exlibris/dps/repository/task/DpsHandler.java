package com.exlibris.dps.repository.task;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.HandlerBase;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.RepInfo;

public class DpsHandler extends HandlerBase {

	private RepInfo repinfo;

	public DpsHandler() {
		super("DPSHandler","v1",new int[3],"note","right");
	}

	public RepInfo getRepinfo() {
		return repinfo;
	}

	@Override
	public void show() {
		// TODO DpsHandler.show

	}

	@Override
	public void show(Module module) {
		// TODO DpsHandler.show

	}

	@Override
	public void show(RepInfo repinfo) {
		this.repinfo = repinfo;
	}

	@Override
	public void show(OutputHandler outputhandler) {
		// TODO DpsHandler.show

	}

	@Override
	public void show(App app) {
		// TODO DpsHandler.show

	}

	@Override
	public void showFooter() {
		// TODO DpsHandler.showFooter

	}

	@Override
	public void showHeader() {
		// TODO DpsHandler.showHeader

	}

}
