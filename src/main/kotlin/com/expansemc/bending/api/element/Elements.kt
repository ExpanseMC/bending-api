package com.expansemc.bending.api.element

import com.expansemc.bending.api.registry.CatalogRegistry
import com.expansemc.bending.api.registry.getLazy
import com.expansemc.bending.api.util.NamespacedKeys.bending

object Elements {

    /**
     * Airbending, one of the four elemental bending arts, is the aerokinetic
     * ability to control and manipulate air.
     *
     * Air is the element of freedom. The Air Nomads detached themselves
     * from worldly problems and concerns; finding peace and freedom was the
     * key to solving their difficulties in life. Airbenders continually
     * sought spiritual enlightenment, and, as a result, all children born
     * into the Air Nomads were benders. The first airbenders learned their
     * art from the flying bison.
     */
    val AIR: Element by CatalogRegistry.instance.getLazy(bending("air"))

    /**
     *
     *
     * Chi blocking is an ancient technique that has been practiced in secret
     * for centuries. Blocking someone's chi renders the victim's muscles
     * useless and temporarily disables a bender's abilities. It is mainly used
     * by nonbenders as a way of self-defense or even to attack.
     */
    val CHI: Element by CatalogRegistry.instance.getLazy(bending("chi"))

    /**
     * Earthbending, one of the four elemental bending arts, is the geokinetic
     * ability to manipulate earth and rock in all their various forms.
     *
     * Earth is the element of substance, while the people of the Earth
     * Kingdom are diverse, strong, and enduring.[1] Following the lion
     * turtles' decision to relinquish their role as protectors of mankind, Oma
     * and Shu were the first earthbenders to learn this art from the badgermoles.
     */
    val EARTH: Element by CatalogRegistry.instance.getLazy(bending("earth"))

    /**
     * Firebending, one of the four elemental bending arts, is the pyrokinetic
     * ability to control fire.
     *
     * Fire is the element of power, consisting of overpowering force
     * tempered by the unflinching will to accomplish tasks and desires.
     * However, during the Hundred Year War, a militaristic Fire Nation twisted
     * this into firebending being fueled by rage, hatred, and anger.
     * Firebending draws its power from the sun, and the first human
     * firebenders derived their firebending techniques from the dragons.
     */
    val FIRE: Element by CatalogRegistry.instance.getLazy(bending("fire"))

    /**
     * Waterbending, one of the four elemental bending arts, is the hydrokinetic
     * ability to control water in all of its various forms.
     *
     * Water is the element of change. The moon is the source of power in
     * waterbending, and the original waterbenders learned to bend by observing
     * how the moon pushed and pulled the tides.
     */
    val WATER: Element by CatalogRegistry.instance.getLazy(bending("water"))
}