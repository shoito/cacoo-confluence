package com.github.shoito.confluence.integration.cacoo.macro;

import java.util.Map;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.*;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.pages.thumbnail.Dimensions;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.github.shoito.confluence.integration.cacoo.util.DiagramImageUtil;
import org.apache.velocity.VelocityContext;
import org.slf4j.LoggerFactory;

public class CacooMacro implements Macro, EditorImagePlaceholder {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CacooMacro.class);
    public static final String PARAM_DIAGRAM_URL = "diagram-url";

    private static final String RESOURCE_FOLDER = "/download/resources/com.github.shoito.confluence.integration.cacoo/";
    private static final String PLACEHOLDER_SERVLET = "/plugins/servlet/cacoo-macro/placeholder";
    private static final int IMG_WIDTH = 400;
    private static final int IMG_HEIGHT = 300;
    private static final String DEFAULT_DIAGRAM_URL = "https://cacoo.com/diagrams/pByowlpiZ7YTV7UN";
    private static final String TEMPLATE = "template/embed.vm";
    private final PageManager pageManager;
    private final SettingsManager settingsManager;

    public CacooMacro(PageManager pageManager, SettingsManager settingsManager) {
        this.pageManager = pageManager;
        this.settingsManager = settingsManager;
    }

    @Override
    public String execute(Map<String, String> params, String bodyContent,
                          ConversionContext conversionContext) throws MacroExecutionException {
        String diagramUrl = params.get(PARAM_DIAGRAM_URL);
        diagramUrl = (diagramUrl != null) ? diagramUrl : DEFAULT_DIAGRAM_URL;

        VelocityContext context = new VelocityContext(MacroUtils.defaultVelocityContext());
        context.put("url", diagramUrl);
        return VelocityUtils.getRenderedTemplate(TEMPLATE, context);
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }

    @Override
    public ImagePlaceholder getImagePlaceholder(Map<String, String> params, ConversionContext conversionContext) {
        String diagramUrl = params.get(PARAM_DIAGRAM_URL);
        diagramUrl = (diagramUrl != null) ? diagramUrl : DEFAULT_DIAGRAM_URL;

        if (!DiagramImageUtil.validateUrl(diagramUrl + ".png")) {
            return new DefaultImagePlaceholder(RESOURCE_FOLDER + "images/placeholder.png", new Dimensions(IMG_WIDTH, IMG_HEIGHT), true);
        }

        return new DefaultImagePlaceholder(String.format("%s?%s=%s", PLACEHOLDER_SERVLET, PARAM_DIAGRAM_URL, diagramUrl), new Dimensions(IMG_WIDTH, IMG_HEIGHT), true);
    }
}
