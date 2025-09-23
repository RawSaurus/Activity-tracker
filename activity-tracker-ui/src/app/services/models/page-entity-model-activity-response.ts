/* tslint:disable */
/* eslint-disable */
import { EntityModelActivityResponse } from '../models/entity-model-activity-response';
import { PageableObject } from '../models/pageable-object';
import { SortObject } from '../models/sort-object';
export interface PageEntityModelActivityResponse {
  content?: Array<EntityModelActivityResponse>;
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
