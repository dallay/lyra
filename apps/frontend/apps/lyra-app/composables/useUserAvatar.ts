import { ref } from 'vue';
import { randomNumber, avatar } from '#imports';
import type { IUser } from '@/domain/user';

export function useUserAvatar(user: IUser | null) {
  const defaultAvatar = `/avatars/0${randomNumber(1, 5)}.png`;
  const userAvatar = ref(user?.email ? avatar(user.email) : defaultAvatar);

  return {
    userAvatar,
  };
}
