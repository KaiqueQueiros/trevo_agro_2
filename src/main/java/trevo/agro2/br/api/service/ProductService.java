package trevo.agro2.br.api.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import trevo.agro2.br.api.dto.product.ProductDto;
import trevo.agro2.br.api.exceptions.models.BadRequestException;
import trevo.agro2.br.api.model.Budget;
import trevo.agro2.br.api.model.Product;
import trevo.agro2.br.api.repository.BudgetRepository;
import trevo.agro2.br.api.repository.ProductRepository;
import trevo.agro2.br.api.utils.ResponseModel;
import trevo.agro2.br.api.utils.ResponseModelMessage;
import trevo.agro2.br.api.utils.ResponseModelObject;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private BudgetRepository budgetRepository;

    public ResponseEntity<ResponseModel> register(@RequestBody @Valid ProductDto dto) {
        if (productRepository.existsByName(dto.name())) {
            throw new BadRequestException("Nome de produto ja existe");
        }
        Product product = new Product(dto);
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseModelObject("Produto salvo com sucesso", product), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseModel> list() {
        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) {
            throw new BadRequestException("Lista esta vazia");
        }
        return new ResponseEntity<>(new ResponseModelObject("Lista de produtos", list), HttpStatus.OK);
    }

    public ResponseEntity<ResponseModel> findById(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new BadRequestException("Produto não encontrado");
        }
        return new ResponseEntity<>(new ResponseModelObject("Detalhes do produto " + product.getName(), product), HttpStatus.OK);
    }

    public ResponseEntity<ResponseModel> delete(@PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        List<Budget> budgetRepositoryList = budgetRepository.findByProducts(product);
        if (!productRepository.existsById(id) || product == null) {
            throw new BadRequestException("Produto não encontrado");
        }
        if (budgetRepositoryList.isEmpty()) {
            productRepository.deleteById(id);
            return new ResponseEntity<>(new ResponseModelMessage("Produto excluido"), HttpStatus.OK);
        }
        throw new BadRequestException("Produto relacionado com orçamentos!");
    }

    public ResponseEntity<ResponseModel> update(@RequestBody @Valid ProductDto dto, @PathVariable UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (!productRepository.existsById(id) || product == null) {
            throw new BadRequestException("Produto não encontrado");
        }
        product.update(dto);
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseModelObject("Produto atualizado", product), HttpStatus.OK);
    }
}
