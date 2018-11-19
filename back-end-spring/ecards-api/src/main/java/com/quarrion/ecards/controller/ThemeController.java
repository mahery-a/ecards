package com.quarrion.ecards.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.quarrion.ecards.exception.ResourceNotFoundException;
import com.quarrion.ecards.model.Theme;
import com.quarrion.ecards.repository.ThemeRepository;

@RestController
@RequestMapping("/api")
@Api(value="themes", description="manage the greeting ecards themes")
public class ThemeController {

	@Autowired
    private ThemeRepository themeRepository;

    @ApiOperation(value = "View a list of available themes",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/theme", method= RequestMethod.GET, produces = "application/json")
    public Iterable<Theme> list(Model model){
        Iterable<Theme> themeList = themeRepository.findAll();
        return themeList;
    }
    @ApiOperation(value = "Search a theme with an ID",response = Theme.class)
    @RequestMapping(value = "/theme/{id}", method= RequestMethod.GET, produces = "application/json")
    public Theme showTheme(@PathVariable Long id, Model model){
       Theme theme = themeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Theme", "id", id));
        return theme;
    }

    @ApiOperation(value = "Add a theme")
    @RequestMapping(value = "/theme", method = RequestMethod.POST, produces = "application/json")
    public Theme saveTheme(@RequestBody Theme theme){
        return themeRepository.save(theme);
    }

    @ApiOperation(value = "Update a theme")
    @RequestMapping(value = "/theme/{id}", method = RequestMethod.PUT, produces = "application/json")
    public Theme updateTheme(@PathVariable Long id, @RequestBody Theme theme){
        Theme storedTheme = themeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Theme", "id", id));
        storedTheme.setName(theme.getName());
        return themeRepository.save(storedTheme);
    }

    @ApiOperation(value = "Delete a theme")
    @RequestMapping(value="/theme/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@PathVariable Long id){
        themeRepository.deleteById(id);
    }

}
