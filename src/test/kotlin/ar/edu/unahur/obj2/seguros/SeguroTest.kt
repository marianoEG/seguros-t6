package ar.edu.unahur.obj2.seguros

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class SeguroTest : DescribeSpec({

  // instanciando los vehiculos
  val c3 = Auto(550000,5)
  val partner = Auto(650000,11)
  val falcon = Auto (75000, 45)

  val iveco = Camion(1500500,5)
  val mercedez = Camion(2100000,10)
  val ford = Camion(1050000,25)

  val corsa = Taxi(750000,13,true)
  val bora = Taxi(1200000,3,false)

  // ahora los seguros
  val seguroContraTercero = ContraTerceros()
  val seguroContraRobo = RoboHurto()
  val seguroContraTodo = TodoRiesgo()

  describe("1.- Determinar si un vehículo puede contratar un determinado seguro.") {

    it("seguro contra tercero") {

      // veremos primero autos
      c3.puedeContratar(seguroContraTercero).shouldBeTrue()
      falcon.puedeContratar(seguroContraTercero).shouldBeTrue()

      // ahora los camiones
      mercedez.puedeContratar(seguroContraTercero).shouldBeTrue()

      // por ultimo, los taxis
      corsa.puedeContratar(seguroContraTercero).shouldBeTrue()
    }

    it("seguro contra robo") {
      // primero los autos
      partner.puedeContratar(seguroContraRobo).shouldBeTrue()

      // despues los camiones
      // primero los mas nuevitos
      iveco.puedeContratar(seguroContraRobo).shouldBeTrue()
      // ahora, uno mas viejito
      ford.puedeContratar(seguroContraRobo).shouldBeFalse()
      // y este pega en el palo
      mercedez.puedeContratar(seguroContraRobo).shouldBeTrue()

      // por ultimo los taxis
      bora.puedeContratar((seguroContraRobo)).shouldBeTrue()
      // ahora uno viejito
      corsa.puedeContratar(seguroContraRobo).shouldBeFalse()
    }
    it("seguro todo riesgo") {
      // autos primero
      c3.puedeContratar(seguroContraTodo).shouldBeTrue()

      // camiones y taxis
      iveco.puedeContratar(seguroContraTodo).shouldBeFalse()
      corsa.puedeContratar(seguroContraTodo).shouldBeFalse()
    }
  }

  describe("2.- Saber cuánto costaría un seguro para un vehículo determinado") {

    it("probando con casos que pueden tener el seguro contra tercero") {
      // auto con un seguro contra tercero
      c3.cuantoCuesta(seguroContraTercero).shouldBe(5500)

      // camion con un seguro contra tercero
      iveco.cuantoCuesta(seguroContraTercero).shouldBe(22507.50) // tiene antiguedad de 5 años
      ford.cuantoCuesta(seguroContraTercero).shouldBe(21000)  // tiene antiguedad de mas de 10 años

      // taxi con un seguro contra tercero
      corsa.cuantoCuesta(seguroContraTercero).shouldBe(16000)  // tiene infracciones
      bora.cuantoCuesta(seguroContraTercero).shouldBe(24000)  // no tiene infracciones
    }

    it("probando con casos que pueden tener el seguro contra robo") {
      // auto con seguro contra robo
      partner.cuantoCuesta(seguroContraRobo).shouldBe(32500)   // seguro contra robo
      falcon.cuantoCuesta(seguroContraRobo).shouldBe(2250)   // seguro contra robo en autos fabricados en 1995 ó después

      // camion con seguro contra robo
      iveco.cuantoCuesta(seguroContraRobo).shouldBe(75025)

      // taxi con seguro contra robo
      bora.cuantoCuesta(seguroContraRobo).shouldBe(60000)
    }

    it("probando con casos que pueden tener el seguro contra todo riesgo") {
      // auto con seguro contra todo riesgo
      partner.cuantoCuesta(seguroContraTodo).shouldBe(45500.00000000001)
    }
    it("probando con cosas que dan error") {
      // casos con seguro de contra robo
      shouldThrowAny {ford.cuantoCuesta(seguroContraRobo).shouldBe(543543)}  // camion mayor a 10 años
      shouldThrowAny {corsa.cuantoCuesta(seguroContraRobo).shouldBe(3452300)}    // taxi mayor a 12 años

      // casos con seguro contra todo riesgo
      shouldThrowAny {iveco.cuantoCuesta(seguroContraTodo).shouldBe(34500)}   // los camiones no pueden tener este segur
      shouldThrowAny {corsa.cuantoCuesta(seguroContraTodo).shouldBe((23000))}  // los taxis no pueden este seguro
    }
  }

  describe("3.- Contratar un seguro: lo cual implica que el vehículo pasa a efectivamente contar con ese seguro") {
    it("Agregar un seguro") {
      // probemos con el seguro contra tercero y seguro contra todo riesgo
      c3.seguros.size.shouldBe(0)
      c3.contratar(seguroContraTercero)
      c3.contratar(seguroContraTodo)
      c3.seguros.size.shouldBe(2)
    }
    it("error al agregar el seguro") {
      shouldThrowAny {corsa.contratar(seguroContraTodo)}
    }
  }

  describe("4.- Conocer el costo total que un vehículo abona en concepto de seguros") {
    it("sumamos el total de costos del seguro") {
      c3.contratar(seguroContraTercero)  //  5500
      c3.contratar(seguroContraTodo)   // 38500
      c3.costoTotalDeSeguros().shouldBe(44000)
    }
  }
})
