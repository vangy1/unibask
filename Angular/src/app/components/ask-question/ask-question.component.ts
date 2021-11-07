import {Component, OnDestroy, ViewChild} from '@angular/core';
import {QuillEditorBase} from "ngx-quill";
import {QuestionService} from "../../entry/question/question.service";
import {Router} from "@angular/router";
import {CategoryService} from "../../category/category.service";
import {FormControl} from "@angular/forms";
import {ReplaySubject, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {Category} from "../../category/category";

@Component({
  selector: 'app-question-ask',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.scss']
})
export class AskQuestionComponent implements OnDestroy {
  @ViewChild('editor', {static: false}) quillEditorBase: QuillEditorBase;
  title: string;
  text: string;
  isAnonymous: boolean;


  public categoryFilterCtrl: FormControl = new FormControl();
  public filteredCategories: ReplaySubject<Category[]> = new ReplaySubject<Category[]>(1);

  categories: Category[]
  selectedCategory: Category;

  protected _onDestroy = new Subject<void>();


  constructor(private router: Router, private questionService: QuestionService, private categoryService: CategoryService) {
    this.categoryService.getLeafCategories().subscribe((categories) => {
      this.categories = categories;

      this.filteredCategories.next(this.categories.slice());

      // listen for search field value changes
      this.categoryFilterCtrl.valueChanges
        .pipe(takeUntil(this._onDestroy))
        .subscribe(() => {
          this.filterCategories();
        });
    })


  }

  filterCategories() {
    if (!this.categories) {
      return;
    }

    let search = this.categoryFilterCtrl.value;
    if (!search) {
      this.filteredCategories.next(this.categories.slice());
      return;
    } else {
      search = search.toLowerCase();
    }

    this.filteredCategories.next(
      this.categories.filter(category => {
        return (category.title + this.getPath(category)).toLowerCase().indexOf(search) > -1
      })
    );
  }

  getPath(category: Category) {
    return '[' + category.path.join(', ') + ']'
  }

  ngOnInit(): void {
  }

  createNewQuestion() {
    this.questionService.createNewQuestion(this.title, this.text, this.selectedCategory.id, this.isAnonymous).subscribe(id => {
      this.router.navigate(['/question'], {queryParams: {id: id}})
    });
  }

  ngOnDestroy() {
    this._onDestroy.next();
    this._onDestroy.complete();
  }

}
