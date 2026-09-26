import { ProfileForm } from "@/features/user/components/profile-form";
import { getCurrentUser } from "@/lib/auth/session";

export default async function ProfilePage(){
    const user = await getCurrentUser();

    return (
        <main className="p-8">
            <h1 className="text-2xl font-bold">Profile</h1>

            <p className="mt-2 text-zinc-400">
                Manage your personal information.
            </p>

            <ProfileForm
                firstName={user?.firstName ?? ""}
                lastName={user?.lastName ?? ""}
            />
        </main>
    );
}
