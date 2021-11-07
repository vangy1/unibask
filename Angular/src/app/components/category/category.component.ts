import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../category/category.service";
import {Category} from "../../category/category";
import {NestedTreeControl} from "@angular/cdk/tree";
import {MatTreeNestedDataSource} from "@angular/material/tree";
import {Router} from "@angular/router";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
})
export class CategoryComponent implements OnInit {

  categories: Category[]
  treeControl = new NestedTreeControl<Category>(node => node.childrenCategories);
  dataSource = new MatTreeNestedDataSource<Category>();

  constructor(private categoryService: CategoryService, private router: Router) {
  }

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((next) => {
      this.categories = next;
      this.dataSource.data = this.categories;

    })
  }

  hasChild = (_: number, node: Category) => !!node.childrenCategories && node.childrenCategories.length > 0;


  favoriteCategory(id: number) {
    console.log(id)
    this.categoryService.favoriteCategory(id).subscribe()
  }

  goToCategory(category: Category) {
    this.router.navigate(['/list'], {queryParams: {categoryId: category.id}})

  }
}
