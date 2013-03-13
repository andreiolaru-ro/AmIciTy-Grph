echo Pulling changes from %1.
echo This assumes you have git installed on your system and in PATH (executing the command 'git' in console writes the possible git operations).
echo If anything goes wrong, please use the command line to execute commands in this file.
echo Working in:
pwd
git checkout %2
echo ====================================
echo Ready to fetch (ready to insert passphrase)...
read -p "Press [Enter] to continue..."
ssh-agent bash -c "ssh-add $HOME/.ssh/githubkey; git fetch"
git status
echo ====================================
echo Ready to pull (ready to insert passphrase); otherwise close this window
read -p "Press [Enter] to continue..."
ssh-agent bash -c "ssh-add $HOME/.ssh/githubkey; git pull origin %2"
echo ====================================
echo Supposedly done.
read -p "Press [Enter] to continue..."
exit