import {Component, OnInit} from '@angular/core';
import {CategoryService} from "./category.service";
import {Category} from "./category";
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
      this.treeControl.expand(this.categories[0]);
      this.treeControl.expand(this.categories[1]);
    })
  }

  hasChild = (_: number, node: Category) => !!node.childrenCategories && node.childrenCategories.length > 0;


  changeFollowStatus(category: Category) {
    category.followed = !category.followed
    this.categoryService.changeFollowStatus(category.id, category.followed).subscribe()
  }

  goToCategory(category: Category) {
    this.router.navigate(['/list'], {queryParams: {category: category.id}})

  }
}
