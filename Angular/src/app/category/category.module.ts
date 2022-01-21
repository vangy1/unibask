import {NgModule} from '@angular/core';
import {CategoryComponent} from "./category.component";
import {SharedModule} from "../shared.module";
import {MatTreeModule} from "@angular/material/tree";
import {CategoryService} from "./category.service";
import {RouterModule, Routes} from "@angular/router";


const routes: Routes = [
  {path: '', component: CategoryComponent},
];

@NgModule({
  declarations: [CategoryComponent],
  imports: [
    SharedModule,
    MatTreeModule,
    RouterModule.forChild(routes)
  ],
  providers: [CategoryService]
})
export class CategoryModule {
}
