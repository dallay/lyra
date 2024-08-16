import gravatarUrl from 'gravatar-url';

export default function (email: string, size = 100) {
  return gravatarUrl(email, {size});
}
