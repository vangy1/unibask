import {StudyProgram} from "../profile/study-program";

export interface User {
  id: number
  mail: string;
  creationDate: Date;
  username: string;
  avatar: string;
  roles: string[];
  reputation: number;
  studyProgram: StudyProgram;
}
