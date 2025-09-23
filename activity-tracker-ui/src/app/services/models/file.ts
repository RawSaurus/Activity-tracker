/* tslint:disable */
/* eslint-disable */
import { Profile } from '../models/profile';
export interface File {
  fileCode?: string;
  fileId?: number;
  filePath?: string;
  name?: string;
  postedBy?: Profile;
  size?: number;
}
