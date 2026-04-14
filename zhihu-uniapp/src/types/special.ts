export interface SpecialItem {
  id: string | number | undefined;
  title: string;
  cover: string;
  introduction: string;
  isFollowing: boolean;
  updateLabel: string;
  visitLabel: string;
  followersLabel: string;
}

export interface SpecialPageResult {
  list: SpecialItem[];
  total: number;
}