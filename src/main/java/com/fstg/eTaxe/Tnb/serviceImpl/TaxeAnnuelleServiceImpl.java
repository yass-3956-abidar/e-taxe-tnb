/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fstg.eTaxe.Tnb.serviceImpl;

import com.fstg.eTaxe.Tnb.bean.Proprietaire;
import com.fstg.eTaxe.Tnb.bean.TauxTaxe;
import com.fstg.eTaxe.Tnb.bean.TaxeAnnuelle;
import com.fstg.eTaxe.Tnb.bean.Terrain;
import com.fstg.eTaxe.Tnb.dao.TauxTaxeDao;
import com.fstg.eTaxe.Tnb.dao.TaxeAnnuelleDao;
import com.fstg.eTaxe.Tnb.service.TauxTaxeService;
import com.fstg.eTaxe.Tnb.service.TaxeAnnuelleService;
import com.fstg.eTaxe.Tnb.service.TerrainService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ali
 */
@Service
public class TaxeAnnuelleServiceImpl implements TaxeAnnuelleService {

    @Autowired
    private TaxeAnnuelleDao taxeAnnuelleDao;

    @Autowired
    private TerrainService terrainService;

    @Autowired
    private TauxTaxeDao tauxTaxeDao;

    @Override
    public TaxeAnnuelle findByAnneeAndTerrainAndProprietaire(int annee, Terrain Terrain, Proprietaire proprietaire) {
        return taxeAnnuelleDao.findByAnneeAndTerrainAndProprietaire(annee, Terrain, proprietaire);
    }

    @Override
    public void save(TaxeAnnuelle taxeAnnuelle) {
        taxeAnnuelleDao.save(calculeMontant2(taxeAnnuelle));
    }

    @Override
    public List<TaxeAnnuelle> findAll() {
        return taxeAnnuelleDao.findAll();
    }

    //  tested(Ali)
    @Override
    public List<TaxeAnnuelle> taxesNonPayeeByProprietaire(Proprietaire proprietaire) {
        List<TaxeAnnuelle> taxeAnnuelles = new ArrayList<>();

        proprietaire.getTerrains().forEach((terrain) -> {
            terrain.getTaxeAnnuelles().stream().filter((taxeAnnulle) -> (taxeAnnulle.getPayee().equals(false))).forEachOrdered((taxeAnnulle) -> {
                taxeAnnuelles.add(taxeAnnulle);
            });
        });
        return taxeAnnuelles;
    }

    @Override
    public List<TaxeAnnuelle> taxesNonPayeeByTerrain(Terrain terrain) {
        List<TaxeAnnuelle> taxeAnnuelles = new ArrayList<>();
        terrain.getTaxeAnnuelles().stream().filter((taxeAnnulle) -> (taxeAnnulle.getPayee().equals(false))).forEachOrdered((taxeAnnulle) -> {
            taxeAnnuelles.add(taxeAnnulle);
        });
        return taxeAnnuelles;
    }

    @Override
    public List<TaxeAnnuelle> findTaxesNonPayeeByReferanceTerrain(Terrain terrain) {
        List<TaxeAnnuelle> taxeAnnuelles = new ArrayList<>();
        terrain.getTaxeAnnuelles().stream().filter((taxeAnnulle) -> (taxeAnnulle.getPayee().equals(false))).forEachOrdered((taxeAnnulle) -> {
            taxeAnnuelles.add(taxeAnnulle);
        });
        return taxeAnnuelles;
    }

    //For Save
    @Override
    public TaxeAnnuelle calculeMontant2(TaxeAnnuelle taxeAnnuelle) {
        BigDecimal montant = new BigDecimal(BigInteger.ZERO);
        montant = (terrainService.findById(taxeAnnuelle.getTerrain().getId()).getSurface()).multiply(tauxTaxeDao.findById(taxeAnnuelle.getTauxTaxe().getId()).get().getMontantTaxe());
        taxeAnnuelle.setMontant(montant);
        return taxeAnnuelle;
    }

//    //there is a NullPointerException here !!!!!!!!!!!!!!
//    //For Update 
//    @Override
//    public TaxeAnnuelle calculeMontant(TaxeAnnuelle taxeAnnuelle) {
//        BigDecimal montant = new BigDecimal(BigInteger.ZERO);
//        montant = (taxeAnnuelle.getTerrain().getSurface()).multiply(taxeAnnuelle.getTauxTaxe().getMontantTaxe());
//        taxeAnnuelle.setMontant(montant);
//        return taxeAnnuelle;
//    }

    // not implemented yet
//    @Override
//    public void update(TaxeAnnuelle taxeAnnuelle) {
//         taxeAnnuelleDao.save(taxeAnnuelle);
//    }
    // not implemented yet
    @Override
    public List<Integer> anneestaxesNonPayeeByReferanceTerrain(String referance) {
        return null;
    }

    @Override
    public TaxeAnnuelle findById(Long id) {
        return taxeAnnuelleDao.findById(id).get();
    }

    //khdama ^_^
    @Override
    public void update(Long id, TaxeAnnuelle taxeAnnuelle) {
        TaxeAnnuelle taxeAnnuelle2 = new TaxeAnnuelle();
        taxeAnnuelle2 = findById(id);
        if (taxeAnnuelle.getTauxTaxe() != null) {
            TauxTaxe tauxTaxe = tauxTaxeDao.findById(taxeAnnuelle.getTauxTaxe().getId()).get();
            taxeAnnuelle2.setTauxTaxe(tauxTaxe);
        } 
        if (true) {
            Boolean payee = taxeAnnuelle.getPayee();
            taxeAnnuelle2.setPayee(payee);
        }

        save(taxeAnnuelle2);
    }
}
