/*
 * Copyright (c) 2022.
 * @Author: Moises I da Silva
 * Email: dev.moises.dasilva@gmail.com
 */

package com.moises.todo.todorestapi.infrastructure.service.pdf;

import com.moises.todo.todorestapi.domain.service.ToDoListPdfService;
import com.moises.todo.todorestapi.domain.service.TodoItemService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PdfTodoListService implements ToDoListPdfService {

    @Autowired
    private TodoItemService todoItemService;

    @Override
    public byte[] issueTodoList( Long todoListId) {

        try {
            var inputStream = this.getClass().getResourceAsStream("/pdf/todo-list.jasper");

            var todoItemList = todoItemService.findItemsByTodoList(todoListId);
            var dataSource = new JRBeanCollectionDataSource(todoItemList);

            var parameters = new HashMap<String, Object>();

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new PdfException("Unable to issue the ToDo List in PDF.", e);
        }

    }

}
