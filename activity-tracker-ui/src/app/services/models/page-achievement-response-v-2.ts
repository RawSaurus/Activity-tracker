/* tslint:disable */
/* eslint-disable */
import { AchievementResponseV2 } from '../models/achievement-response-v-2';
import { PageableObject } from '../models/pageable-object';
import { SortObject } from '../models/sort-object';
export interface PageAchievementResponseV2 {
  content?: Array<AchievementResponseV2>;
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: PageableObject;
  size?: number;
  sort?: SortObject;
  totalElements?: number;
  totalPages?: number;
}
