package com.example.buildings.service;

import com.example.buildings.dto.ProductDto;
import com.example.buildings.entity.Employee;
import com.example.buildings.entity.Product;
import com.example.buildings.entity.Project;
import com.example.buildings.entity.Unit;
import com.example.buildings.repository.EmployeeRepository;
import com.example.buildings.repository.ProductRepository;
import com.example.buildings.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EmployeeRepository employeeRepository;
    private final UnitRepository unitRepository;
    private final ProjectService projectService;

    public List<Product> getListByProjectId(Long projectId) {
        return productRepository.getListByProjectId(projectId);
    }

    public List<Product> getList() {
        return productRepository.findAll();
    }

    public boolean save(ProductDto productDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(productDto.getUserId());
        if (optionalEmployee.isEmpty())
            return false;
        Optional<Unit> optionalUnit = unitRepository.findById(productDto.getUnitId());
        if (optionalUnit.isEmpty())
            return false;
        Project project = null;
        if (productDto.getProjectId() != null) {
            project = projectService.getById(productDto.getProjectId());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Unit unit = optionalUnit.get();
        Employee employee = optionalEmployee.get();
        productDto.setProjectId(employee.getProject().getId());
        if (project == null)
            project = employee.getProject();
        Product productFromDb = productRepository.getByName(productDto.getName(), productDto.getUnitId(), productDto.getProjectId());
        if (productFromDb == null) {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setCreatedDate(sdf.format(cal.getTime()).toString());
            product.setCreatedBy(employee);
            product.setQuantity(productDto.getQuantity());
            product.setProject(project);
            product.setUnit(unit);
            productRepository.save(product);
            return true;
        }
        productFromDb.setQuantity(productFromDb.getQuantity() + productDto.getQuantity());
        productFromDb.setOver(false);
        productRepository.save(productFromDb);
        return true;
    }

    public Product getById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty())
            return null;
        return optionalProduct.get();
    }

    public boolean edited(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());
        if (optionalProduct.isEmpty())
            return false;
        Product productFromDb = optionalProduct.get();
        if (productFromDb.getQuantity() - productDto.getQuantity() > 0) {
            productFromDb.setQuantity(productFromDb.getQuantity() - productDto.getQuantity());
            productRepository.save(productFromDb);
            return true;
        } else if (productFromDb.getQuantity() - productDto.getQuantity() == 0) {
            productFromDb.setQuantity(productFromDb.getQuantity() - productDto.getQuantity());
            productFromDb.setOver(true);
            productRepository.save(productFromDb);
            return true;
        }
        return false;
    }
}
