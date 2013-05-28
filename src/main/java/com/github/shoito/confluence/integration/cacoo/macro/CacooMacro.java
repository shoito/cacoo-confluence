package com.github.shoito.confluence.integration.cacoo.macro;

import java.util.Map;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.macro.GenericVelocityMacro;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.LoggerFactory;

public class CacooMacro extends GenericVelocityMacro implements Macro {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CacooMacro.class);
    private final PageManager pageManager;
    private final SettingsManager settingsManager;

    public CacooMacro(PageManager pageManager, SettingsManager settingsManager) {
        this.pageManager = pageManager;
        this.settingsManager = settingsManager;
    }

    @Override
    public String execute(Map<String, String> params, String bodyContent,
                          ConversionContext conversionContext) throws MacroExecutionException {
        String cacooUrl = params.get("diagram-url");
        cacooUrl = (cacooUrl != null) ? cacooUrl : "https://cacoo.com/diagrams/pByowlpiZ7YTV7UN";
        String width = params.get("width");
        width = (width != null) ? width : "640";
        String height = params.get("height");
        height = (height != null) ? height : "480";

        VelocityContext context = new VelocityContext(MacroUtils.defaultVelocityContext());
        context.put("url", cacooUrl);
        context.put("width", width);
        context.put("height", height);
        return VelocityUtils.getRenderedTemplate("template/embed.vm", context);
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }

}
