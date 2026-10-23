package com.royals.class11physics

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FormulaAndDerivationDataTest {

    @Test
    fun testChaptersIntegrity() {
        assertEquals("Should have exactly 15 NCERT chapters", 15, chapters.size)
        for (i in 1..15) {
            val chapter = chapters.find { it.number == i }
            assertNotNull("Chapter $i should exist", chapter)
            assertTrue("Chapter $i title should not be blank", chapter!!.title.isNotBlank())
            assertTrue("Chapter $i filename should end with .html", chapter.fileName.endsWith(".html"))
            assertTrue("Chapter $i unit should not be blank", chapter.unit.isNotBlank())
        }
    }

    @Test
    fun testPhysicsFormulasIntegrity() {
        assertTrue("Physics formulas dataset should have extensive items", physicsFormulas.size >= 30)

        val uniqueIds = mutableSetOf<String>()
        val chaptersWithFormulas = mutableSetOf<Int>()

        for (item in physicsFormulas) {
            assertTrue("Formula ID must be unique: ${item.id}", uniqueIds.add(item.id))
            assertTrue("Chapter number must be between 1 and 15", item.chapterNumber in 1..15)
            chaptersWithFormulas.add(item.chapterNumber)

            assertTrue("Topic must not be blank for ${item.id}", item.topic.isNotBlank())
            assertTrue("Formula must not be blank for ${item.id}", item.formula.isNotBlank())
            assertTrue("Description must not be blank for ${item.id}", item.description.isNotBlank())
            assertTrue("Symbol meanings must not be blank for ${item.id}", item.symbolMeanings.isNotBlank())
            assertTrue("Importance must be valid for ${item.id}", item.examImportance in listOf("Crucial", "Must Know", "High Yield"))
        }

        for (i in 1..15) {
            assertTrue("Chapter $i must have formula items", chaptersWithFormulas.contains(i))
        }
    }

    @Test
    fun testPhysicalConstantsIntegrity() {
        assertTrue("Should have at least 10 Class 11 physical constants", physicalConstants.size >= 10)

        val symbols = physicalConstants.map { it.symbol }
        assertTrue("Should contain G (Gravitational constant)", symbols.any { it.contains("G") })
        assertTrue("Should contain g (Earth gravity)", symbols.any { it.contains("g") })
        assertTrue("Should contain R (Gas constant)", symbols.any { it.contains("R") })
        assertTrue("Should contain k_B (Boltzmann)", symbols.any { it.contains("k_B") })
        assertTrue("Should contain σ (Stefan-Boltzmann)", symbols.any { it.contains("σ") })
        assertTrue("Should contain 1 atm (Standard pressure)", symbols.any { it.contains("atm") })

        for (c in physicalConstants) {
            assertTrue("Constant name must not be blank", c.name.isNotBlank())
            assertTrue("Constant symbol must not be blank", c.symbol.isNotBlank())
            assertTrue("Constant value must not be blank", c.value.isNotBlank())
            assertTrue("Constant unit must not be blank", c.unit.isNotBlank())
            assertTrue("Constant category must not be blank", c.category.isNotBlank())
        }
    }

    @Test
    fun testQuantityDimensionsIntegrity() {
        assertTrue("Should have at least 20 quantity dimensions", quantityDimensions.size >= 20)

        for (qd in quantityDimensions) {
            assertTrue("Quantity name must not be blank", qd.quantity.isNotBlank())
            assertTrue("Symbol must not be blank", qd.symbol.isNotBlank())
            assertTrue("Formula must not be blank", qd.formula.isNotBlank())
            assertTrue("SI unit must not be blank", qd.siUnit.isNotBlank())
            assertTrue("Dimensional formula must not be blank", qd.dimensionalFormula.isNotBlank())
            assertTrue("Dimensional formula must contain brackets [ ]", qd.dimensionalFormula.startsWith("[") && qd.dimensionalFormula.endsWith("]"))
        }
    }

    @Test
    fun testTopDerivationsIntegrity() {
        assertEquals("Should have exactly 15 high-yield derivations", 15, topDerivations.size)

        val derivationIds = mutableSetOf<String>()
        val requiredTopics = listOf(
            "projectile", "centripetal", "banking", "work_energy",
            "collision", "altitude_depth", "escape", "terminal",
            "bernoulli", "pendulum", "isothermal", "ideal_gas",
            "rolling", "capillary", "standing_waves"
        )

        for (keyword in requiredTopics) {
            val exists = topDerivations.any { it.id.contains(keyword) || it.title.contains(keyword, ignoreCase = true) }
            assertTrue("Must include derivation for $keyword", exists)
        }

        for (d in topDerivations) {
            assertTrue("Derivation ID must be unique: ${d.id}", derivationIds.add(d.id))
            assertTrue("Derivation title must not be blank", d.title.isNotBlank())
            assertTrue("Chapter title must not be blank", d.chapterTitle.isNotBlank())
            assertTrue("Statement must not be blank", d.statement.isNotBlank())
            assertTrue("Diagram description must not be blank", d.diagramDescription.isNotBlank())
            assertTrue("Final expression must not be blank", d.finalExpression.isNotBlank())
            assertTrue("Exam tips must not be blank", d.examTips.isNotBlank())
            assertTrue("Must have at least 3 derivation steps", d.steps.size >= 3)
            assertFalse("Key assumptions must not be empty", d.keyAssumptions.isEmpty())

            for (step in d.steps) {
                assertTrue("Step number must be > 0", step.stepNumber > 0)
                assertTrue("Step title must not be blank", step.title.isNotBlank())
                assertTrue("Step explanation must not be blank", step.explanation.isNotBlank())
                assertTrue("Step equation must not be blank", step.equation.isNotBlank())
            }
        }
    }

    @Test
    fun testNewAddedDerivations() {
        val rolling = topDerivations.find { it.id == "d_rolling_acceleration" }
        assertNotNull("Rolling acceleration derivation must exist", rolling)
        assertEquals("Rolling should be Chapter 7", 7, rolling!!.chapterNumber)
        assertTrue(rolling.finalExpression.contains("1 + k²/R²") || rolling.finalExpression.contains("k^2/R^2") || rolling.finalExpression.contains("sin θ"))

        val capillary = topDerivations.find { it.id == "d_capillary_rise" }
        assertNotNull("Capillary rise derivation must exist", capillary)
        assertEquals("Capillary rise should be Chapter 10", 10, capillary!!.chapterNumber)
        assertTrue(capillary.finalExpression.contains("2 T cos θ") || capillary.finalExpression.contains("2T"))

        val pipe = topDerivations.find { it.id == "d_standing_waves_pipe" }
        assertNotNull("Standing waves pipe derivation must exist", pipe)
        assertEquals("Standing waves should be Chapter 15", 15, pipe!!.chapterNumber)
        assertTrue(pipe.finalExpression.contains("v / (4L)") || pipe.finalExpression.contains("4L"))
    }

    @Test
    fun testFormulaSearchFiltering() {
        val query = "kinetic"
        val matches = physicsFormulas.filter {
            it.topic.contains(query, ignoreCase = true) ||
            it.formula.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
        assertTrue("Searching for 'kinetic' should yield matching formulas", matches.isNotEmpty())

        val ch8Formulas = physicsFormulas.filter { it.chapterNumber == 8 }
        assertTrue("Chapter 8 (Gravitation) should have formulas", ch8Formulas.isNotEmpty())
        assertTrue("Chapter 8 should contain escape velocity or g variation", ch8Formulas.any { it.topic.contains("Escape", ignoreCase = true) || it.topic.contains("Gravity", ignoreCase = true) })
    }

    @Test
    fun testFormulaHubUnitAndDimensionSearch() {
        // Test syllabus unit search
        val kinematicsMatches = physicsFormulas.filter { formula ->
            val chapterUnit = chapters.find { it.number == formula.chapterNumber }?.unit.orEmpty()
            chapterUnit.contains("Kinematics", ignoreCase = true)
        }
        assertTrue("Formulas in Kinematics unit should exist", kinematicsMatches.isNotEmpty())

        // Test dimension SI unit search
        val jouleMatches = quantityDimensions.filter { it.siUnit.contains("joule", ignoreCase = true) || it.siUnit.contains("J", ignoreCase = false) }
        assertTrue("Dimensions with Joule should exist", jouleMatches.isNotEmpty())

        // Test dimensional formula search
        val forceMatches = quantityDimensions.filter { it.dimensionalFormula.contains("M L T⁻²") || it.dimensionalFormula.contains("T⁻²") }
        assertTrue("Dimensions with T⁻² should exist", forceMatches.isNotEmpty())
    }

    @Test
    fun testDerivationFiltering() {
        val fiveMarkers = topDerivations.filter { it.marksWeightage.contains("5") }
        assertTrue("Should have multiple 5-mark derivations", fiveMarkers.size >= 5)

        val ch10Derivations = topDerivations.filter { it.chapterNumber == 10 }
        assertTrue("Chapter 10 (Fluids) should have derivations", ch10Derivations.isNotEmpty())
        assertTrue("Chapter 10 should have Bernoulli's or Terminal velocity or Capillary rise", ch10Derivations.any {
            it.title.contains("Bernoulli", ignoreCase = true) ||
            it.title.contains("Terminal", ignoreCase = true) ||
            it.title.contains("Capillary", ignoreCase = true)
        })
    }

    @Test
    fun testDerivationChapterAliasSearch() {
        fun searchDerivations(q: String): List<Derivation> {
            val query = q.trim()
            val chNum = Regex("""(?:ch|chapter)\s*(\d+)""", RegexOption.IGNORE_CASE)
                .find(query)?.groupValues?.get(1)?.toIntOrNull()
                ?: query.toIntOrNull()

            return topDerivations.filter { item ->
                (chNum != null && item.chapterNumber == chNum) ||
                item.title.contains(query, ignoreCase = true) ||
                item.statement.contains(query, ignoreCase = true) ||
                item.finalExpression.contains(query, ignoreCase = true) ||
                item.examTips.contains(query, ignoreCase = true)
            }
        }

        val ch4Matches = searchDerivations("ch 4")
        assertTrue("Search 'ch 4' should match Chapter 4 derivations", ch4Matches.isNotEmpty() && ch4Matches.all { it.chapterNumber == 4 })

        val ch8Matches = searchDerivations("chapter 8")
        assertTrue("Search 'chapter 8' should match Chapter 8 derivations", ch8Matches.isNotEmpty() && ch8Matches.all { it.chapterNumber == 8 })

        val bernoulliMatches = searchDerivations("bernoulli")
        assertTrue("Search 'bernoulli' should match Bernoulli derivation", bernoulliMatches.isNotEmpty())
    }
}
