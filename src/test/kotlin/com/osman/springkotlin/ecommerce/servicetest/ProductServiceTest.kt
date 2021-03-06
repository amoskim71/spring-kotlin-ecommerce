package com.osman.springkotlin.ecommerce.servicetest


import com.osman.springkotlin.ecommerce.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
internal class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var inventoryService: InventoryService


    @InjectMocks
    lateinit var productService: ProductService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

    }

    @Test(expected = IllegalArgumentException::class)
    fun `test create a product that already exist fail`() {
        `when`(productRepository.findById(ArgumentMatchers.anyString())).thenReturn(getTestProduct())
        productService.create(getTestProduct().get(), 200)
        verify(productRepository, times(0)).save(any<Product>())

    }

    @Test
    fun `test adding product sucsess`() {
        `when`(productRepository.save(any<Product>())).thenReturn(getTestProduct().get())
        val returnedproduct = productService.create(getTestProduct().get(), 200)

        verify(productRepository, times(1)).save(any<Product>())
        assertEquals(returnedproduct.name, getTestProduct().get().name)
    }

    @Test
    fun `test get all products`() {
        `when`(productRepository.findAll()).thenReturn(listOf(getTestProduct().get(), getTestProduct2().get()))
        val products = productService.getAll()

        verify(productRepository, times(1)).findAll()
        assertNotNull(products)
        assertEquals(2, products.size)

    }

    @Test
    fun `test deleting product success`() {
        `when`(productRepository.existsById(ArgumentMatchers.anyString())).thenReturn(true)
        productService.delete(getTestProduct().get().id!!)
        verify(productRepository, times(1)).deleteById(ArgumentMatchers.anyString())
        assertEquals(0, productService.getAll().size)

    }





}
