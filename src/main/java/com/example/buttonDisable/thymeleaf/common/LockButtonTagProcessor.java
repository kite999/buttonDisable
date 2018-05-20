package com.example.buttonDisable.thymeleaf.common;

import com.example.buttonDisable.lock.LockEntity;
import com.example.buttonDisable.lock.LockService;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.*;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class LockButtonTagProcessor extends AbstractAttributeTagProcessor {
    private static final int PRECEDENCE = Integer.MAX_VALUE;
    private static final String ATTR_NAME = "lockButton";
    private final LockService lockService;

    public LockButtonTagProcessor(String dialectPrefix, LockService lockService) {
        super(TemplateMode.HTML, dialectPrefix, null, false, ATTR_NAME, true, PRECEDENCE, true);
        this.lockService = lockService;
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
                             AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {

        IModelFactory modelFactory = context.getModelFactory();
        IModel iModel = modelFactory.createModel();
        String buttonText = "編集";
        String attributeTarget = tag.getAttributeValue("target");

        String query = tag.getAttributeValue("query");
        if (attributeTarget != null && !attributeTarget.isEmpty()) {
            final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(context.getConfiguration());
            final IStandardExpression expression = expressionParser.parseExpression(context, attributeTarget);
            final Object attributeTargetValue = expression.execute(context);

            LockEntity lockEntity = this.lockService.getByTarget(attributeTargetValue.toString());
            //TODO セッションユーザIDと取得できたユーザIDが同じならユーザ名を表示するなど考慮ください。
            if (lockEntity != null && lockEntity.getUserId() > 0) {
                buttonText = lockEntity.getUserId() + "が編集中";
            }
        }
        IOpenElementTag buttonTag = modelFactory.createOpenElementTag("button" ,"name", "lockButton");
        buttonTag = modelFactory.setAttribute(buttonTag, "class", "lockButton");
        buttonTag = modelFactory.setAttribute(buttonTag, "data-query", query);
        
        iModel.add(buttonTag);
        iModel.add(modelFactory.createText(HtmlEscape.escapeHtml4Xml(buttonText)));
        iModel.add(modelFactory.createCloseElementTag("button"));
        structureHandler.replaceWith(iModel, false);
    }
}