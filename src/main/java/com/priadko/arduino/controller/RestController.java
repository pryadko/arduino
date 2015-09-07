package com.priadko.arduino.controller;

import com.priadko.arduino.entry.Measure;
import com.priadko.arduino.services.DataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/measure")
public class RestController {

    @Autowired
    private DataService dataService;

    public static final Logger LOG = Logger.getLogger(RestController.class);

    /* Submit form in Spring Restful Services */
/*    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void createMeasure(@RequestBody Measure measure) {
        try {
            dataService.create(measure);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }*/

    @RequestMapping(value = "last/{typeMeasure}", method = RequestMethod.GET)
    public
    @ResponseBody
    Measure getEmployee(@PathVariable("typeMeasure") String typeName) {
        Measure measure = null;
        try {
            measure = dataService.getLastValuesByType(typeName);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return measure;
    }

    /* Getting List of objects in Json format in Spring Restful Services */
/*    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<Measure> getEmployee() {

        List<Measure> measureList = null;
        try {
            measureList = dataService.getAll();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return measureList;
    }*/

  /*  *//* Delete an object from DB in Spring Restful Services *//*
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Status deleteEmployee(@PathVariable("id") long id) {

        try {
            dataService.deleteEntity(id);
            return new Status(1, "Employee deleted Successfully !");
        } catch (Exception e) {
            return new Status(0, e.toString());
        }

    }*/
}
