/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.eTaxe.Tnb.rest;

import com.fstg.eTaxe.Tnb.bean.Quartier;
import com.fstg.eTaxe.Tnb.bean.Rue;
import com.fstg.eTaxe.Tnb.bean.Secteur;
import com.fstg.eTaxe.Tnb.service.QuartierService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author yassine
 */
@RestController
@RequestMapping("/e-Taxe-Tnb/quartier")
public class QuartierRest {

    @Autowired
    public QuartierService quartierService;

    
    @GetMapping(value = "/liblle/{libelle}")
    public Quartier findByLibelle(@PathVariable String libelle) {
        return quartierService.findByLibelle(libelle);
    }

//    @GetMapping(value = "/rue/")
//    public Quartier findByRue(@RequestBody Rue rue) {
//        return quartierService.findByRue(rue);
//    }

//    @GetMapping(value = "/secteur/")
//    public List<Quartier> findBySecteur(@RequestBody Secteur secteur) {
//        return quartierService.findBySecteur(secteur);
//    }

    //tested
    @PostMapping(value = "/")
    public void save(@RequestBody Quartier quartier) {
        quartierService.save(quartier);
    }

    //tested
    @DeleteMapping(value = "/id/{id}")
    public void deleteQuartier(@PathVariable long id) {
        quartierService.deleteQuartier(id);
    }

    //tested
    @GetMapping(value = "/")
    public List<Quartier> findAll() {
        return quartierService.findAll();
    }
}
